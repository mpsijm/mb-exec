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
import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.IStrategoTermList;

public class SSL_printnl extends Primitive {

    protected SSL_printnl() {
        super("SSL_printnl", 0, 2);
    }

    public boolean call(IContext env, List<Strategy> sargs, List<IStrategoTerm> targs) throws InterpreterException {

        // FIXME: Possibly erroneous
        String output = Tools.javaString(targs.get(0));

        IStrategoTermList l = Tools.consToListDeep(env, (IStrategoAppl) targs.get(1));

        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < l.size(); i++) {
            IStrategoTerm t = Tools.termAt(l, i);
            if (Tools.isTermAppl(t)) {
                IStrategoAppl a = (IStrategoAppl)t;
                if (Tools.isCons(a, env))
                    sb.append(Tools.consToListDeep(env, a));
                else if (Tools.isTermString(t))
                    sb.append(Tools.javaString(t));
                continue;
            }
            sb.append(t.toString());
        }

        if(output.equals("stderr"))
            System.err.println(sb);
        else if(output.equals("stdout"))
            System.out.println(sb);
        else
            throw new InterpreterException("Unknown output : " + output);
        
        return true;
    }
}
