/*
 * Created on 08.aug.2005
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.library;

import java.util.List;

import org.spoofax.interpreter.IContext;
import org.spoofax.interpreter.InterpreterException;
import org.spoofax.interpreter.Tools;
import org.spoofax.interpreter.stratego.Strategy;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class SSL_getenv extends Primitive {

    protected SSL_getenv() {
        super("SSL_getenv", 0, 1);
    }
    
    public boolean call(IContext env, List<Strategy> sargs, List<IStrategoTerm> targs) throws InterpreterException {
        
        if(!Tools.isTermString(targs.get(0)))
            return false;
        
        String s = System.getenv(Tools.javaString(targs.get(0)));

        if(s == null)
            return false;
        
        env.setCurrent(env.getFactory().makeString(s));
        return true;
    }
}
