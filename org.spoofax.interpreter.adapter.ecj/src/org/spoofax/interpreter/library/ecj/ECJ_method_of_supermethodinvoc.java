/*
 * Created on 9. okt.. 2006
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.library.ecj;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.spoofax.interpreter.IConstruct;
import org.spoofax.interpreter.IContext;
import org.spoofax.interpreter.InterpreterException;
import org.spoofax.interpreter.adapter.ecj.ECJFactory;
import org.spoofax.interpreter.adapter.ecj.WrappedASTNode;
import org.spoofax.interpreter.library.AbstractPrimitive;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class ECJ_method_of_supermethodinvoc extends AbstractPrimitive {

    public ECJ_method_of_supermethodinvoc() {
        super("ECJ_method_of_supermethodinvoc", 0, 1);
    }
    
    @Override
    public boolean call(IContext env, IConstruct[] svars, IStrategoTerm[] tvars)
            throws InterpreterException {
        
        if(!(tvars[0] instanceof WrappedASTNode))
            return false;
        
        WrappedASTNode n = (WrappedASTNode) tvars[0];
        if(!(n.getWrappee() instanceof SuperMethodInvocation))
            return false;
        
        SuperMethodInvocation m = (SuperMethodInvocation) n.getWrappee();
        
        IMethodBinding mb = m.resolveMethodBinding();
        if(mb == null)
            return false;
        
        env.setCurrent(ECJFactory.wrap(mb));
        return true;
    }

}