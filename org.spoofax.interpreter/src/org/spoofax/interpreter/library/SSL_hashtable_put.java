/*
 * Created on 08.aug.2005
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.library;

import java.util.List;

import org.spoofax.interpreter.InterpreterException;
import org.spoofax.interpreter.IContext;
import org.spoofax.interpreter.Tools;
import org.spoofax.interpreter.library.SSL_hashtable_create.ATermHashtable;
import org.spoofax.interpreter.stratego.Strategy;

import aterm.ATerm;
import aterm.ATermInt;

public class SSL_hashtable_put extends Primitive {

    protected SSL_hashtable_put() {
        super("SSL_hashtable_put", 0, 3);
    }
    
    public boolean call(IContext env, List<Strategy> sargs, List<ATerm> targs) throws InterpreterException {

        if(!(targs.get(0).getType() == ATerm.INT))
            return false;

        ATermHashtable ath = SSL.getHashtable(((ATermInt)targs.get(0)).getInt());
        if(ath == null)
            return false;
        
        ath.put(targs.get(1), targs.get(2));
        env.setCurrent(targs.get(0));
        return true;
    }
}
