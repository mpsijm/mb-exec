/*
 * Created on 2. okt.. 2006
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.adapter.ecj;

import org.eclipse.jdt.core.dom.LineComment;
import org.spoofax.interpreter.terms.IStrategoConstructor;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class WrappedLineComment extends WrappedComment {

    private static final long serialVersionUID = 1L;

    // FIXME where is the content?
    private final LineComment wrappee;
    private final static IStrategoConstructor CTOR = new ASTCtor("LineComment", 0);
    
    WrappedLineComment(LineComment wrappee) {
        super(CTOR);
        this.wrappee = wrappee;
    }
    
    @Override
    public IStrategoTerm getSubterm(int index) {
	
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public LineComment getWrappee() {
        return wrappee;
    }

}
