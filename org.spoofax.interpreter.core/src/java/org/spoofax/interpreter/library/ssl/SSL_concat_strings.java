/*
 * Created on 08.aug.2005
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
import org.spoofax.interpreter.stratego.Strategy;
import org.spoofax.interpreter.terms.IStrategoList;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class SSL_concat_strings extends AbstractPrimitive {

    protected SSL_concat_strings() {
        super("SSL_concat_strings", 0, 1);
    }

    @Override
    public boolean call(IContext env, Strategy[] sargs, IStrategoTerm[] targs) throws InterpreterException {

        
        if(!Tools.isTermList(targs[0]))
            return false;
        String result = call((IStrategoList) targs[0]);
        if (result == null)
            return false;
        env.setCurrent(env.getFactory().makeString(result));
        return true;
    }

    public static String call(IStrategoList list) {
        IStrategoTerm[] kids = list.getAllSubterms();
        
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < kids.length; i++) {
            if(!Tools.isTermString(kids[i]))
                return null;
            sb.append(Tools.asJavaString(kids[i]));
        }
        return sb.toString();
    }
}
