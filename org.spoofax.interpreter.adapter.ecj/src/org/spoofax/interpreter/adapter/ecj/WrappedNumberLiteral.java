/*
 * Created on 1. okt.. 2006
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.adapter.ecj;

import org.eclipse.jdt.core.dom.NumberLiteral;
import org.spoofax.interpreter.terms.IStrategoConstructor;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class WrappedNumberLiteral extends WrappedExpression {

    private static final long serialVersionUID = 1L;

    private final NumberLiteral wrappee;
    private final static IStrategoConstructor CTOR = new ASTCtor("NumberLiteral", 1);
    
    WrappedNumberLiteral(NumberLiteral wrappee) {
        super(CTOR);
        this.wrappee = wrappee;
    }
    @Override
    public IStrategoTerm getSubterm(int index) {
        if(index == 0)
            return ECJFactory.wrap(wrappee.getToken());
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public NumberLiteral getWrappee() {
        return wrappee;
    }
}
