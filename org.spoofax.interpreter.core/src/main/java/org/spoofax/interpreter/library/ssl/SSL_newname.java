package org.spoofax.interpreter.library.ssl;

import java.util.HashSet;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.spoofax.interpreter.core.IContext;
import org.spoofax.interpreter.core.InterpreterException;
import org.spoofax.interpreter.library.AbstractPrimitive;
import org.spoofax.interpreter.stratego.Strategy;
import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoString;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;
import org.spoofax.terms.util.TermUtils;

public class SSL_newname extends AbstractPrimitive {
    private static final WeakHashMap<IContext, WeakHashMap<String, AtomicInteger>> countersPerContext =
        new WeakHashMap<>();

    private final HashSet<String> prefixes = new HashSet<>();


    SSL_newname() {
        super("SSL_newname", 0, 1);
    }


    @Override public boolean call(IContext env, Strategy[] svars, IStrategoTerm[] tvars) throws InterpreterException {
        final IStrategoTerm prefixTerm = tvars[0];
        String prefix;
        if(TermUtils.isString(prefixTerm)) {
            final IStrategoString str = (IStrategoString) prefixTerm;
            prefix = str.stringValue();
        } else if(TermUtils.isAppl(prefixTerm)) {
            final IStrategoAppl appl = (IStrategoAppl) prefixTerm;
            prefix = appl.getConstructor().getName();
        } else {
            final SSLLibrary library = (SSLLibrary) env.getOperatorRegistry(SSLLibrary.REGISTRY_NAME);
            return library.get("SSL_new").call(env, svars, tvars);
        }
        
        // Intern to ensure that we get the same hard-reference to the prefix string, which will be used as a key in
        // the countersPerContext weak hashmap.
        prefix = prefix.intern();

        final ITermFactory factory = env.getFactory();

        synchronized(countersPerContext) {
            WeakHashMap<String, AtomicInteger> counters;
            counters = countersPerContext.get(env);
            if(counters == null) {
                counters = new WeakHashMap<>();
                countersPerContext.put(env, counters);
            }

            AtomicInteger counter;
            counter = counters.get(prefix);
            prefixes.add(prefix);
            if(counter == null) {
                counter = new AtomicInteger();
                counters.put(prefix, counter);
            }

            String result;
            IStrategoTerm resultTerm;
            do {
                int counterValue = getNextValue(env, counter);
                result = prefix + counterValue;
                resultTerm = factory.tryMakeUniqueString(result);
            } while(resultTerm == null);

            env.setCurrent(factory.replaceTerm(resultTerm, prefixTerm));
            return true;
        }
    }

    private int getNextValue(IContext env, AtomicInteger counter) {
        int result;
        while(true) {
            result = counter.getAndIncrement();
            if(result >= 0) {
                break;
            } else if(counter.compareAndSet(result, 0)) {
                ((SSLLibrary) env.getOperatorRegistry(SSLLibrary.REGISTRY_NAME))
                    .getIOAgent()
                    .printError("SSL_newname: counter wrapped around");
                return 0;
            }
        }
        return result;
    }
}
