/*
 * Created on 12. jan.. 2007
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk near strategoxt.org>
 * 
 * Licensed under the GNU Lesser General Public License, v2.1
 */
package org.spoofax.interpreter.library.ssl;

import org.spoofax.interpreter.core.IContext;
import org.spoofax.interpreter.core.InterpreterException;
import org.spoofax.interpreter.core.Tools;
import org.spoofax.interpreter.library.AbstractPrimitive;
import org.spoofax.interpreter.stratego.CallT;
import org.spoofax.interpreter.stratego.Strategy;
import org.spoofax.interpreter.terms.IStrategoList;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class SSL_list_loop extends AbstractPrimitive {

    SSL_list_loop() {
        super("SSL_list_loop", 1, 1);
    }
    
    @Override
    public boolean call(IContext env, Strategy[] svars, IStrategoTerm[] tvars)
            throws InterpreterException {
        
        if(!Tools.isTermList(tvars[0]))
            return false;
        
        CallT s = (CallT) svars[0];
        Strategy[] sv = new Strategy[0];
        IStrategoTerm[] tv = new IStrategoTerm[0];
        
        IStrategoTerm saved = env.current();
        
        for (IStrategoList list = (IStrategoList) tvars[0]; !list.isEmpty(); list = list.tail()) {
            env.setCurrent(list.head());
            if(!s.evaluateWithArgs(env, sv, tv))
                return false;
        }
        
        env.setCurrent(saved);
        return true;
    }

}
