/*
 * Created on 9. okt.. 2006
 *
 * Copyright (c) 2005, Karl Trygve Kalleberg <karltk@ii.uib.no>
 * 
 * Licensed under the GNU General Public License, v2
 */
package org.spoofax.interpreter.demo;

import java.util.List;

import org.eclipse.jdt.core.dom.Type;
import org.spoofax.interpreter.IContext;
import org.spoofax.interpreter.InterpreterException;
import org.spoofax.interpreter.adapters.ecj.ECJFactory;
import org.spoofax.interpreter.adapters.ecj.WrappedASTNode;
import org.spoofax.interpreter.library.Primitive;
import org.spoofax.interpreter.stratego.Strategy;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class ECJ_type_of_type extends Primitive {

    public ECJ_type_of_type() {
        super("ECJ_type_of_type", 0, 1);
    }
    
    @Override
    public boolean call(IContext env, List<Strategy> svars, IStrategoTerm[] tvars)
            throws InterpreterException {
        
        if(!(tvars[0] instanceof WrappedASTNode))
            return false;
        
        WrappedASTNode n = (WrappedASTNode) tvars[0];
        if(n.getWrappee() instanceof Type)
            return false;
        
        Type t = (Type) n.getWrappee();
        
        env.setCurrent(ECJFactory.wrap(t.resolveBinding()));
        return true;
    }

}