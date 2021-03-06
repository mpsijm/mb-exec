/*
 * Created on 07.aug.2005
 *
 * Copyright (c) 2004, Karl Trygve Kalleberg <karltk near strategoxt.org>
 * 
 * Licensed under the GNU Lesser General Public License, v2.1
 */
package org.spoofax.interpreter.stratego;

import java.util.ArrayList;

import org.spoofax.interpreter.core.IConstruct;
import org.spoofax.interpreter.core.IContext;
import org.spoofax.interpreter.core.InterpreterException;
import org.spoofax.interpreter.core.Pair;
import org.spoofax.interpreter.core.Tools;
import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoInt;
import org.spoofax.interpreter.terms.IStrategoReal;
import org.spoofax.interpreter.terms.IStrategoRef;
import org.spoofax.interpreter.terms.IStrategoString;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.IStrategoList;
import org.spoofax.interpreter.terms.IStrategoTuple;
import org.spoofax.interpreter.util.DebugUtil;
import org.spoofax.terms.util.NotImplementedException;
import org.spoofax.terms.util.TermUtils;


import static org.spoofax.interpreter.core.Context.debug;
import static org.spoofax.terms.util.TermUtils.toApplAt;

public class Match extends Strategy {

    // FIXME pre-process to avoid String.equals() for Op cases
    protected IStrategoAppl pattern;

    public Match(IStrategoAppl pattern) {
        this.pattern = pattern;
    }

    public IConstruct eval(IContext env) throws InterpreterException {
        
        debug("Match.eval() - ", " !", env.current(), " ; ?", pattern);

        IStrategoTerm current = env.current();

        Results r = match(env, current, pattern);

        if (r == null) {
            return getHook().pop().onFailure(env);
        }
        else {
            boolean b = env.bindVars(r);
            if (b)
            	return getHook().pop().onSuccess(env);
            else
            	return getHook().pop().onFailure(env);
        }
    }

    public Results matchAppl(IContext env, IStrategoAppl t,
            IStrategoAppl p) throws InterpreterException {

        if (Tools.isAnno(p, env)) {
            return matchAnyAnno(env, t, p);
        }
        else if (Tools.isOp(p, env)) {
            return matchApplOp(env, t, p);
        }
        else if (Tools.isInt(p, env)) {
            return matchApplInt(env, t, p);
        }
        else if (Tools.isStr(p, env)) {
            return null;
        }
        else if (Tools.isVar(p, env)) {
            return matchAnyVar(t, p);
        }
        else if (Tools.isExplode(p, env)) {
            return matchAnyExplode(env, t, p);
        }
        else if (Tools.isAs(p, env)) {
            return matchCompoundAs(env, t, p);
        }
        else if (Tools.isWld(p, env)) {
            return matchAnyWld(p);
        }

        throw new InterpreterException("Unknown Appl case '" + p + "'");
    }

    protected Results matchApplInt(IContext env, IStrategoTerm t,
            IStrategoAppl p) throws InterpreterException {
        if (TermUtils.isInt(t))
            return match(env, TermUtils.toIntAt(t, 0), toApplAt(p, 0));
        return null;
    }

    protected Results matchApplStr(IStrategoTerm t, IStrategoTerm p) {

        throw new NotImplementedException();

        //if (t.equals(p))
        //    return emptyList();
        //return null;
    }

    protected Results matchApplOp(IContext env, IStrategoAppl t,
            IStrategoAppl p) throws InterpreterException {


        String c = TermUtils.toJavaStringAt(p, 0);
        if(c.equals("Cons")) {
            return null; //matchApplCons(env, t, p);
        } else if(c.equals("Nil")) {
            return null; //matchApplNil(env, t);
        } 

        IStrategoList ctorArgs = TermUtils.toListAt(p, 1);

        // Check if arity of the pattern matches that
        // of the term
        if (ctorArgs.getSubtermCount() != t.getSubtermCount())
            return null;

        // Check if the constructor name in the pattern
        // matches that of the term
        if (!t.getConstructor().getName().equals(c))
            return null;

        // Recursively match all arguments to term
        Results r = emptyList();
        for (int i = 0; i < ctorArgs.size(); i++) {
            Results m = match(env, t.getSubterm(i), TermUtils.toApplAt(ctorArgs, i));
            if (m != null)
                r.addAll(m);
            else
                return null;
        }

        return r;
    }

//    private Results matchApplCons(IContext env, IStrategoAppl t, IStrategoAppl p) {
//        throw new NotImplementedException();
//    }
//
//    private Results matchApplNil(IContext env, IStrategoAppl t) {
//        throw new NotImplementedException();
//    }

    private Results emptyList() {
        return new Results();
    }


    protected Results matchInt(IContext env, IStrategoInt t,
            IStrategoAppl p) throws InterpreterException {

        debug("term is Int");

        if (Tools.isAnno(p, env)) {
            return matchAnyAnno(env, t, p);
        }
        else if (Tools.isInt(p, env)) {
            return matchIntInt(t, p);
        }
        else if (Tools.isReal(p, env)) {
            return null;
        }
        else if (Tools.isVar(p, env)) {
            return matchAnyVar(t, p);
        }
        else if (Tools.isOp(p, env)) {
            return null;
        }
        else if (Tools.isExplode(p, env)) {
            return matchAnyExplode(env, t, p);
        }
        else if (Tools.isWld(p, env)) {
            return matchAnyWld(p);
        }
        else if (Tools.isAs(p, env)) {
            return matchCompoundAs(env, t, p);
        } else if(Tools.isStr(p, env)) {
            return null;
        }

        throw new InterpreterException("Unknown Int case '" + p + "'");
    }

    protected Results matchReal(IContext env, IStrategoReal t,
            IStrategoAppl p) throws InterpreterException {

        debug("term is Real");

        if (Tools.isAnno(p, env)) {
            return matchAnyAnno(env, t, p);
        }
        else if (Tools.isInt(p, env)) {
            return null;
        }
        else if (Tools.isReal(p, env)) {
            return matchRealReal(t, p);
        }
        else if (Tools.isVar(p, env)) {
            return matchAnyVar(t, p);
        }
        else if (Tools.isOp(p, env)) {
            return null;
        }
        else if (Tools.isExplode(p, env)) {
            return matchAnyExplode(env, t, p);
        }
        else if (Tools.isWld(p, env)) {
            return matchAnyWld(p);
        }
        else if (Tools.isAs(p, env)) {
            return matchCompoundAs(env, t, p);
        } else if (Tools.isStr(p, env)) {
            return null;
        }

        throw new InterpreterException("Unknown Real case '" + p + "'");
    }

    private Results matchRealReal(IStrategoReal t, IStrategoAppl p) {

        Double realVal = new Double(TermUtils.toJavaStringAt(p, 0));

        if (realVal == t.realValue())
            return emptyList();

        return null;
    }

    private Results matchAnyWld(IStrategoAppl p) {
        return emptyList();
    }

    protected Results matchAnyAnno(IContext env, IStrategoTerm t,
            IStrategoAppl p) throws InterpreterException {
        
        Results r1 = matchList(env, t.getAnnotations(), toApplAt(p, 1));
        if (r1 == null) return null;
        
        Results r2 = match(env, t, toApplAt(p, 0));
        if (r2 == null) return null;
        
        r2.addAll(r1);
        return r2;
    }

    protected Results matchIntInt(IStrategoInt t, IStrategoAppl p) {
        Integer intVal = new Integer(TermUtils.toJavaStringAt(p, 0));
        if (intVal == t.intValue())
            return emptyList();

        return null;
    }

    /*
    protected Results matchAnyAs(IStrategoTerm t, IStrategoAppl p) {
        String varName = TermUtils.toJavaStringAt(TermUtils.toApplAt(p, 0), 0);
        return newResult(new Binding(varName, t));
    }*/

    @SuppressWarnings("serial")
    public static final class Results extends ArrayList<Binding> {
        // empty
    }

    public static final class Binding extends Pair<String, IStrategoTerm> {
        public Binding(String first, IStrategoTerm second) {
            super(first, second);
        }
    }

    private Results newResult(Binding initial) {
        Results r = new Results();
        r.add(initial);
        return r;
    }

    private Results matchAnyExplode(IContext env, IStrategoTerm t,
            IStrategoAppl p) throws InterpreterException {

        DebugUtil.debug("  pattern is Explode");
        IStrategoAppl opPattern = toApplAt(p, 0);
        IStrategoAppl argsPattern = toApplAt(p, 1);

        IStrategoTerm op = getTermConstructor(env, t);
        IStrategoTerm args = getTermArguments(env, t);

        Results opResult = match(env, op, opPattern);
        Results argsResult = match(env, args, argsPattern);

        if (opResult == null || argsResult == null)
            return null;

        opResult.addAll(argsResult);

        return opResult;
    }

    private IStrategoTerm getTermArguments(IContext env, IStrategoTerm t) throws InterpreterException {

        switch(t.getTermType()) {
        case IStrategoTerm.INT:
        case IStrategoTerm.REAL:
            return env.getFactory().makeList();
        case IStrategoTerm.APPL:
            IStrategoAppl a = (IStrategoAppl)t;
            if (Tools.isNil(a, env) || Tools.isCons(a, env))
                return t;
            else
                return env.getFactory().makeList(a.getAllSubterms());
        case IStrategoTerm.LIST: 
            return t;
        case IStrategoTerm.STRING:
            return env.getFactory().makeList();
        case IStrategoTerm.TUPLE:
            IStrategoTuple tup = (IStrategoTuple) t;
            IStrategoTerm[] args = new IStrategoTerm[tup.getSubtermCount()];
            for(int i = 0; i < args.length; i++) 
                args[i] = tup.get(i);
            return env.getFactory().makeList(args); 
        }
            
        throw new InterpreterException("Unknown term '" + t + "'");
    }

    private IStrategoTerm getTermConstructor(IContext env, IStrategoTerm t) throws InterpreterException {
        if (TermUtils.isInt(t) || TermUtils.isReal(t)) {
            return t;
        } else if (TermUtils.isString(t)) {
            return env.getFactory().makeString("\"" + ((IStrategoString)t).stringValue() + "\"");
        } else if (TermUtils.isAppl(t)) {
            IStrategoAppl a = (IStrategoAppl)t;
            if (Tools.isCons(a, env) || Tools.isNil(a, env))
                return env.getFactory().makeAppl(env.getStrategoSignature().getNil());
            else
                return env.getFactory().makeString(((IStrategoAppl)t).getConstructor().getName());
        } else if (TermUtils.isList(t)) {
            return env.getFactory().makeList();
        } else if (TermUtils.isTuple(t)) {
            return env.getFactory().makeString("");
        }

        throw new InterpreterException("Unknown term '" + t + "'");
    }

    public Results match(IContext env, IStrategoTerm t, IStrategoAppl p)
    throws InterpreterException {

    	if (t == null) {
    	    throw new InterpreterException("Null term while matching: term library or one of the primitives is defective");
    	}
        switch (t.getTermType()) {
        case IStrategoTerm.APPL:
            return matchAppl(env, (IStrategoAppl) t, p);
        case IStrategoTerm.INT:
            return matchInt(env, (IStrategoInt) t, p);
        case IStrategoTerm.REAL:
            return matchReal(env, (IStrategoReal) t, p);
        case IStrategoTerm.STRING:
            return matchString(env, (IStrategoString) t, p);
        case IStrategoTerm.LIST:
            return matchList(env, (IStrategoList) t, p);
        case IStrategoTerm.TUPLE:
            return matchTuple(env, (IStrategoTuple) t, p);
        case IStrategoTerm.REF:
            return matchRef(env, (IStrategoRef)t, p);
        case IStrategoTerm.BLOB:
            return matchBlob(env, t, p);
        default:
            throw new InterpreterException("Unsupported term type : "
                                           + t.getClass().toString() + " [" + t.getTermType() + "]");
        }
    }

    private Results matchBlob(IContext env, IStrategoTerm t, IStrategoAppl p) throws InterpreterException {
        debug("term is Blob");
        if (Tools.isAnno(p, env)) {
            return matchAnyAnno(env, t, p);
        }
        else if (Tools.isWld(p, env)) {
            return matchAnyWld(p);
        }
        else if (Tools.isAs(p, env)) {
            return matchCompoundAs(env, t, p);
        }
        else if (Tools.isVar(p, env)) {
            return matchAnyVar(t, p);
        }
        else if (Tools.isExplode(p, env)) {
            return null; // FIXME: explode blobs?
        } 
        else if (Tools.isStr(p, env)) {
            return null;
        }
        else if (Tools.isInt(p, env)) {
            return null;
        }
        else if (Tools.isReal(p, env)) {
            return null;
        }
        else if (Tools.isOp(p, env)) {
            return null;
        }

        throw new InterpreterException("Unknown Tuple case '" + p + "'");
    }

    private Results matchRef(IContext env, IStrategoRef ref, IStrategoAppl p) {
        throw new NotImplementedException();
    }

    private Results matchTuple(IContext env, IStrategoTuple t, IStrategoAppl p) throws InterpreterException {
        debug("term is Tuple");
        if (Tools.isAnno(p, env)) {
            return matchAnyAnno(env, t, p);
        }
        else if (Tools.isInt(p, env)) {
            return null;
        }
        else if (Tools.isReal(p, env)) {
            return null;
        }
        else if (Tools.isVar(p, env)) {
            return matchAnyVar(t, p);
        }
        else if (Tools.isOp(p, env)) {
            return matchTupleOp(env, t, p);
        }
        else if (Tools.isWld(p, env)) {
            return matchAnyWld(p);
        }
        else if (Tools.isAs(p, env)) {
            return matchCompoundAs(env, t, p);
        }
        else if (Tools.isExplode(p, env)) {
            return matchAnyExplode(env, t, p);
        } 
        else if (Tools.isStr(p, env)) {
            return null;
        }

        throw new InterpreterException("Unknown Tuple case '" + p + "'");
    }

    private Results matchTupleOp(IContext env, IStrategoTuple t, IStrategoAppl p) throws InterpreterException {
        
        String c = TermUtils.toJavaStringAt(p, 0);

        // Check that the pattern p is really against a tuple 
        if(!c.equals(""))
            return null;

        IStrategoList ctorArgs = TermUtils.toListAt(p, 1);
        
        // Check that arity of pattern equals arity of tuple
        if(ctorArgs.size() != t.size())
            return null;
        
        // Match subterms of tuple against subpatterns of pattern 
        Results r = emptyList();
        for (int i = 0; i < ctorArgs.size(); i++) {
            Results m = match(env, t.get(i), TermUtils.toApplAt(ctorArgs, i));
            if (m != null)
                r.addAll(m);
            else
                return null;
        }

        return r;
    }

    protected Results matchList(IContext env, IStrategoList t,
            IStrategoAppl p) throws InterpreterException {

        debug("term is List");

        if (Tools.isAnno(p, env)) {
            return matchAnyAnno(env, t, p);
        }
        else if (Tools.isInt(p, env)) {
            return null;
        }
        else if (Tools.isReal(p, env)) {
            return null;
        }
        else if (Tools.isVar(p, env)) {
            return matchAnyVar(t, p);
        }
        else if (Tools.isOp(p, env)) {
            return matchListOp(env, t, p);
        }
        else if (Tools.isWld(p, env)) {
            return matchAnyWld(p);
        }
        else if (Tools.isAs(p, env)) {
            return matchCompoundAs(env, t, p);
        }
        else if (Tools.isExplode(p, env)) {
            return matchAnyExplode(env, t, p);
        }
        else if (Tools.isStr(p, env)) {
            return null;
        }

        throw new InterpreterException("Unknown List case '" + p + "'");
    }

    private Results matchCompoundAs(IContext env, IStrategoTerm t, IStrategoAppl p) throws InterpreterException {
        
        Results r = match(env, t, toApplAt(p, 1));

        if (r == null)
            return null;

        debug("matching CompoundAs", p);

        String varName = TermUtils.toJavaStringAt(toApplAt(p, 0), 0);
        r.add(new Binding(varName, t));

        return r;
    }

    private Results matchListOp(IContext env, IStrategoList t, IStrategoAppl p) throws InterpreterException {
        
        String c = TermUtils.toJavaStringAt(p, 0);

        if(c.equals("Nil")) {
            if(t.isEmpty())
                return emptyList();
        } 
        else if(c.equals("Cons")) {
            
            if(t.size() < 1) {
                return null;
            }
                
            IStrategoTerm head = t.head();
            IStrategoList tail = t.tail();
            
            IStrategoList pattern = TermUtils.toListAt(p, 1);
            
            Results r = match(env, head, TermUtils.toApplAt(pattern, 0));
            if(r == null)
                return null;
            
            Results r2 = match(env, tail, TermUtils.toApplAt(pattern, 1));
            if(r2 == null)
                return null;
            
            r.addAll(r2);
            return r;
        }
        
        return null;
    }

    private Results matchAnyVar(IStrategoTerm t, IStrategoAppl p) {
        String varName = TermUtils.toJavaStringAt(p, 0);
        return newResult(new Binding(varName, t));
    }

    private Results matchString(IContext env, IStrategoString t, IStrategoAppl p) throws InterpreterException {
        debug("term is String");

        if (Tools.isAnno(p, env)) {
            return matchAnyAnno(env, t, p);
        }
        else if(Tools.isStr(p, env)) {
            return matchStrStr(env, t, p);
        }
        else if (Tools.isInt(p, env)) {
            return null;
        }
        else if (Tools.isReal(p, env)) {
            return null;
        }
        else if (Tools.isVar(p, env)) {
            return matchAnyVar(t, p);
        }
        else if (Tools.isOp(p, env)) {
            return null;
        }
        else if (Tools.isExplode(p, env)) {
            return matchAnyExplode(env, t, p);
        }
        else if (Tools.isWld(p, env)) {
            return matchAnyWld(p);
        }
        else if (Tools.isAs(p, env)) {
            return matchCompoundAs(env, t, p);
        } 

        throw new InterpreterException("Unknown String case '" + p + "'");
    }

    private Results matchStrStr(IContext env, IStrategoString t, IStrategoAppl p) {
        DebugUtil.debug("  pattern is Str");
        if (!TermUtils.isStringAt(p, 0, t.stringValue()))
            return null;
        return emptyList();
    }

    public void prettyPrint(StupidFormatter sf) {
        sf.first("Match(" + pattern.toString() + ")");
    }

    @Override
    protected String getTraceName() {
        return super.getTraceName() + "(" + pattern + ")";
    }
}
