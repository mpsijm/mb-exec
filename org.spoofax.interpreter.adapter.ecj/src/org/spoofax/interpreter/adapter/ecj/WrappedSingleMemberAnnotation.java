/*
 * Created on 2. okt.. 2006
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.adapter.ecj;

import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.spoofax.interpreter.terms.IStrategoConstructor;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class WrappedSingleMemberAnnotation extends WrappedAnnotation {

    private static final long serialVersionUID = 1L;
    
    private final SingleMemberAnnotation wrappee;
    private final static IStrategoConstructor CTOR = new ASTCtor("SingleMemberAnnotation", 2);
    
    WrappedSingleMemberAnnotation(SingleMemberAnnotation wrappee) {
        super(CTOR);
        this.wrappee = wrappee;
    }
    
    @Override
    public IStrategoTerm getSubterm(int index) {
        switch(index) {
        case 0:
            return ECJFactory.wrapName(wrappee.getTypeName());
        case 1:
            return ECJFactory.wrapExpression(wrappee.getValue());
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public SingleMemberAnnotation getWrappee() {
        return wrappee;
    }
    
    public IExtendedModifier getModifierWrappee() {
    	return wrappee;
    }

}
