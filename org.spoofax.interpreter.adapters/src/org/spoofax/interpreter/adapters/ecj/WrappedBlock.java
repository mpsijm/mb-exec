/*
 * Created on 1. okt.. 2006
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.adapters.ecj;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
import org.spoofax.interpreter.terms.IStrategoConstructor;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class WrappedBlock extends WrappedASTNode {

    private final Block wrappee;
    private final static IStrategoConstructor CTOR = new ASTCtor("Block", 1); 
    
    WrappedBlock(Block wrappee) {
        super(CTOR);
        this.wrappee = wrappee;
    }
    
    @Override
    public IStrategoTerm getSubterm(int index) {
        if(index == 0)
            return ECJFactory.wrap(wrappee.statements());
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public ASTNode getWrappee() {
        return wrappee;
    }

}