/*
 * Created on 2. okt.. 2006
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.adapters.ecj;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.spoofax.interpreter.terms.IStrategoConstructor;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class WrappedConditionalExpression extends WrappedAppl {

    private final ConditionalExpression wrappee;
    private final static IStrategoConstructor CTOR = new ASTCtor("ConditionalExpression", 3);
    
    WrappedConditionalExpression(ConditionalExpression wrappee) {
        super(CTOR);
        this.wrappee = wrappee;
    }
    
    @Override
    public IStrategoTerm getSubterm(int index) {
        switch(index) {
        case 0: 
            return ECJFactory.wrapExpression(wrappee.getExpression());
        case 1:
            return ECJFactory.wrapExpression(wrappee.getThenExpression());
        case 2:
            return ECJFactory.wrapExpression(wrappee.getElseExpression());
        }
        
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public ASTNode getWrappee() {
        return wrappee;
    }
}
