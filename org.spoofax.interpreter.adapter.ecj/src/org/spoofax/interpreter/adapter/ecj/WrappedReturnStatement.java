/*
 * Created on 2. okt.. 2006
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.adapter.ecj;

import org.eclipse.jdt.core.dom.ReturnStatement;
import org.spoofax.interpreter.terms.IStrategoConstructor;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class WrappedReturnStatement extends WrappedStatement {

    private static final long serialVersionUID = 1L;

    private final ReturnStatement wrappee;
    private final static IStrategoConstructor CTOR = new ASTCtor("ReturnStatement", 1);
    
    WrappedReturnStatement(ReturnStatement wrappee) {
        super(CTOR);
        this.wrappee = wrappee;
    }
    
    @Override
    public IStrategoTerm getSubterm(int index) {
        if(index == 0)
            return ECJFactory.wrapExpression(wrappee.getExpression());
        
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public ReturnStatement getWrappee() {
        return wrappee;
    }
}
