/*
 * Created on 2. okt.. 2006
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.adapter.ecj;

import org.eclipse.jdt.core.dom.MemberValuePair;
import org.spoofax.interpreter.terms.IStrategoConstructor;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class WrappedMemberValuePair extends WrappedASTNode {

    private static final long serialVersionUID = 1L;

    private final MemberValuePair wrappee;
    private final static IStrategoConstructor CTOR = new ASTCtor("MemberValuePair", 2);
    
    WrappedMemberValuePair(MemberValuePair wrappee) {
        super(CTOR);
        this.wrappee = wrappee;
    }
    
    @Override
    public IStrategoTerm getSubterm(int index) {
        switch(index) {
        case 0:
            ECJFactory.wrap(wrappee.getName());
        case 1:
            ECJFactory.wrapExpression(wrappee.getValue());
        }
        
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public MemberValuePair getWrappee() {
        return wrappee;
    }
}
