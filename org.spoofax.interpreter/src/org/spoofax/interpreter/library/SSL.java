/*
 * Created on 08.aug.2005
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.library;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.spoofax.interpreter.library.SSL_hashtable_create.ATermHashtable;

import aterm.ATermInt;

// FIXME: This class contains global scope information
//        but this should be per-environment, not per-library.
public class SSL {

    public final static int CONST_STDERR = 1;
    public final static int CONST_STDOUT = 2;
    public final static int CONST_STDIN = 3;

    // FIXME: Move these into environment

    private static Map<Integer, InputStream> inputStreamMap;
    private static Map<Integer, OutputStream> outputStreamMap;

    private static Map<String, Primitive> registry;

    private static void initRegistry() {
        inputStreamMap = new HashMap<Integer, InputStream>();
        inputStreamMap.put(SSL.CONST_STDIN, System.in);
        
        outputStreamMap = new HashMap<Integer, OutputStream>();
        outputStreamMap.put(SSL.CONST_STDERR, System.err);
        outputStreamMap.put(SSL.CONST_STDOUT, System.out);
        
        registry = new HashMap<String, Primitive>();
        registry.put("SSL_is_int", new SSL_is_int());
        registry.put("SSL_addi", new SSL_addi());
        registry.put("SSL_addr", new SSL_addr());
        registry.put("SSL_divi", new SSL_divi());
        registry.put("SSL_gti", new SSL_gti());
        registry.put("SSL_gtr", new SSL_gtr());
        registry.put("SSL_muli", new SSL_muli());
        registry.put("SSL_mod", new SSL_mod());
        registry.put("SSL_int_to_string", new SSL_int_to_string());
        registry.put("SSL_explode_string", new SSL_explode_string());
        registry.put("SSL_string_to_int", new SSL_string_to_int());
        registry.put("SSL_subti", new SSL_subti());
        registry.put("SSL_subtr", new SSL_subtr());
        registry.put("SSL_new", new SSL_new());
        registry.put("SSL_printnl", new SSL_printnl());
        registry.put("SSL_is_string", new SSL_is_string());
        registry.put("SSL_strcat", new SSL_strcat());
        registry.put("SSL_implode_string", new SSL_implode_string());
        registry.put("SSL_strlen", new SSL_strlen());
        registry.put("SSL_concat_strings", new SSL_concat_strings());
        registry.put("SSL_rand", new SSL_rand());
        registry.put("SSL_getenv", new SSL_getenv());
        registry.put("SSL_cos", new SSL_cos());
        registry.put("SSL_sin", new SSL_sin());
        registry.put("SSL_sqrt", new SSL_sin());
        registry.put("SSL_real_to_string", new SSL_real_to_string());
        registry.put("SSL_real_to_string_precision", new SSL_real_to_string_precision());
        registry.put("SSL_string_to_real", new SSL_string_to_real());
        registry.put("SSL_table_hashtable", new SSL_table_hashtable());
        registry.put("SSL_indexedSet_create", new SSL_indexedSet_create());
        registry.put("SSL_indexedSet_destroy", new SSL_indexedSet_destroy());
        registry.put("SSL_indexedSet_put", new SSL_indexedSet_put());
        registry.put("SSL_indexedSet_getIndex", new SSL_indexedSet_getIndex());
        registry.put("SSL_indexedSet_elements", new SSL_indexedSet_elements());
        registry.put("SSL_indexedSet_remove", new SSL_indexedSet_remove());
        registry.put("SSL_indexedSet_reset", new SSL_indexedSet_reset());
        registry.put("SSL_hashtable_get", new SSL_hashtable_get());
        registry.put("SSL_hashtable_create", new SSL_hashtable_create());
        registry.put("SSL_hashtable_put", new SSL_hashtable_put());
        registry.put("SSL_stderr_stream", new SSL_stderr_stream());
        registry.put("SSL_hashtable_destroy", new SSL_hashtable_destroy());
        registry.put("SSL_hashtable_remove", new SSL_hashtable_remove());
        registry.put("SSL_hashtable_keys", new SSL_hashtable_keys());
        registry.put("SSL_fputs", new SSL_fputs());
        registry.put("SSL_fputc", new SSL_fputc());
        registry.put("SSL_write_term_to_stream_text", new SSL_write_term_to_stream_text());
        registry.put("SSL_access", new SSL_access());
        registry.put("SSL_getcwd", new SSL_getcwd());
     }

    protected static Map<String, Primitive> getRegistry() {
        if (registry == null)
            initRegistry();
        return registry;
    }

    public static Primitive lookup(String s) {
        return getRegistry().get(s);
    }

    public static InputStream inputStreamFromTerm(ATermInt idx) {
        return inputStreamMap.get(new Integer(idx.getInt()));
    }

    public static OutputStream outputStreamFromTerm(ATermInt idx) {
        return outputStreamMap.get(new Integer(idx.getInt()));
    }

    protected static Map<Integer, ATermHashtable> hashtables = new HashMap<Integer, ATermHashtable>();
    protected static int counter = 0;

    public static int registerHashtable(ATermHashtable hashtable) {
        int ref = counter;
        SSL.hashtables.put(SSL.counter++, hashtable);
        return ref;
    }

    public static boolean removeHashtable(int idx) {
        return hashtables.remove(idx) != null;
    }

    public static ATermHashtable getHashtable(int idx) {
        return hashtables.get(idx);
    }
}