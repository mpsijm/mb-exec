/*
 * Created on 08.aug.2005
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.library;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.spoofax.interpreter.IConstruct;
import org.spoofax.interpreter.IContext;
import org.spoofax.interpreter.InterpreterException;
import org.spoofax.interpreter.Tools;
import org.spoofax.interpreter.terms.IStrategoInt;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class SSL_write_term_to_stream_text extends Primitive {

    protected SSL_write_term_to_stream_text() {
        super("SSL_write_term_to_stream_text", 0, 2);
    }
    
    public boolean call(IContext env, List<IConstruct> sargs, IStrategoTerm[] targs) throws InterpreterException {
        
        if(Tools.isTermInt(targs[0]))
            return false;

        SSL or = (SSL) env.getOperatorRegistry(SSL.REGISTRY_NAME);
        OutputStream ous = or.outputStreamFromTerm((IStrategoInt)targs[0]);

        IStrategoTerm t = targs[1];
        
        try {
            env.getFactory().unparseToFile(t, ous);
        } catch (IOException e) {
            throw new InterpreterException(e);
        }
        return true;
    }
}
