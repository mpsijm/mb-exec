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
import org.spoofax.interpreter.stratego.Strategy;

import aterm.ATerm;
import aterm.ATermInt;

public class SSL_mod extends Primitive {

    protected SSL_mod() {
        super("SSL_mod", 0, 2);
    }
    
    public boolean call(IContext env, List<Strategy> sargs, List<ATerm> targs) throws InterpreterException {

        if(!(targs.get(0).getType() == ATerm.INT))
            return false;
        if(!(targs.get(1).getType() == ATerm.INT))
            return false;

        ATermInt a = (ATermInt) targs.get(0);
        ATermInt b = (ATermInt) targs.get(1);
        env.setCurrent(env.getFactory().makeInt(a.getInt() % b.getInt()));
        return true;
    }
}
