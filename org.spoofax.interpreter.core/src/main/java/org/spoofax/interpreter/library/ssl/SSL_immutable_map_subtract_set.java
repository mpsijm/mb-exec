package org.spoofax.interpreter.library.ssl;

import org.spoofax.interpreter.core.IContext;
import org.spoofax.interpreter.core.InterpreterException;
import org.spoofax.interpreter.stratego.Strategy;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class SSL_immutable_map_subtract_set extends SSL_immutable_map_subtract_set_eq {

    protected SSL_immutable_map_subtract_set() {
        super("SSL_immutable_map_subtract_set", 0, 1);
    }

    @Override public boolean call(IContext env, Strategy[] sargs, IStrategoTerm[] targs) throws InterpreterException {
        return subtract(env, targs, Object::equals);
    }
}
