/*
 * Created on 08.aug.2005
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.library;

import java.util.List;

import org.spoofax.interpreter.IConstruct;
import org.spoofax.interpreter.IContext;
import org.spoofax.interpreter.InterpreterException;
import org.spoofax.interpreter.Tools;
import org.spoofax.interpreter.terms.IStrategoInt;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class SSL_indexedSet_destroy extends Primitive {

    protected SSL_indexedSet_destroy() {
        super("SSL_indexedSet_destroy", 0, 1);
    }
    
    public boolean call(IContext env, List<IConstruct> sargs, IStrategoTerm[] targs) throws InterpreterException {

        if(!(Tools.isTermInt(targs[0])))
            return false;

        int ref = ((IStrategoInt)targs[0]).getValue();
        
        SSL_indexedSet_create.map.remove(ref);
        return true;
    }
}
