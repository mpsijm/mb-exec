/*
 * Created on 03.mar.2014
 * 
 * Licensed under the GNU Lesser General Public License, v2.1
 */
package org.spoofax.interpreter.library.ssl;

import org.spoofax.interpreter.core.IContext;
import org.spoofax.interpreter.core.InterpreterException;
import org.spoofax.interpreter.library.AbstractPrimitive;
import org.spoofax.interpreter.stratego.Strategy;
import org.spoofax.interpreter.terms.IStrategoInt;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.terms.util.TermUtils;

public class SSL_shri extends AbstractPrimitive {

    protected SSL_shri() {
        super("SSL_shri", 0, 2);
    }
    
    @Override
    public boolean call(IContext env, Strategy[] sargs, IStrategoTerm[] targs) throws InterpreterException {
        
        if(!TermUtils.isInt(targs[0]))
            return false;
        if(!TermUtils.isInt(targs[1]))
            return false;

        IStrategoInt a = (IStrategoInt) targs[0];
        IStrategoInt b = (IStrategoInt) targs[1];
        env.setCurrent(env.getFactory().makeInt(a.intValue() >> b.intValue()));
        return true;
    }
}
