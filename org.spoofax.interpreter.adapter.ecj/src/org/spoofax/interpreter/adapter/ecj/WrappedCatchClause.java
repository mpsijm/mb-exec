/*
 * Created on 2. okt.. 2006
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.adapter.ecj;

import org.eclipse.jdt.core.dom.CatchClause;
import org.spoofax.interpreter.terms.IStrategoConstructor;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class WrappedCatchClause extends WrappedASTNode {
    
    private static final long serialVersionUID = 1L;

    private final CatchClause wrappee;
    private final static IStrategoConstructor CTOR = new ASTCtor("CatchClause", 2);
    
    WrappedCatchClause(CatchClause wrappee) {
        super(CTOR);
        this.wrappee = wrappee;
    }
    
    @Override
    public IStrategoTerm getSubterm(int index) {
        switch(index) {
        case 0:
            return ECJFactory.wrap(wrappee.getException());
        case 1:
            return ECJFactory.wrap(wrappee.getBody());
        }
        
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public CatchClause getWrappee() {
        return wrappee;
    }

}
