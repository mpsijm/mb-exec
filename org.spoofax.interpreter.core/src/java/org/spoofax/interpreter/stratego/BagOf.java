/*
 * Created on 07.aug.2005
 *
 * Copyright (c) 2004, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.stratego;

import org.spoofax.interpreter.core.IConstruct;
import org.spoofax.interpreter.core.IContext;
import org.spoofax.interpreter.core.InterpreterException;

public class BagOf implements IConstruct {

    public IConstruct eval(IContext e) throws InterpreterException {
        throw new InterpreterException("Unimplemented");
    }

    public void prettyPrint(StupidFormatter sf) {
       sf.append("BAGOF");
    }

	public boolean evaluate(IContext env) throws InterpreterException {
		throw new InterpreterException("Unimplemented");
	}
}
