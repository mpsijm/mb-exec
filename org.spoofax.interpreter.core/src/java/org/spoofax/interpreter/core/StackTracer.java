package org.spoofax.interpreter.core;

import static java.lang.Math.*;

import java.io.PrintStream;

import org.spoofax.interpreter.library.IOAgent;

/**
 * Stack tracing support.
 * 
 * @author Lennart Kats <lennart add lclnet.nl>
 */
public class StackTracer {
    
    private static final int MAX_REPORTED_FRAMES = 130;
    
    private static final int MAX_REPORTED_FRAMES_TAIL = 30;
    
    private IOAgent ioAgent; 
    
    private String[] frames = new String[50];
    
    private int currentDepth;
    
    private int failureDepth;
    
    public void push(String name) {
        int depth = currentDepth++;
        if (frames.length == depth) {
            String[] oldframes = frames;
            frames = new String[frames.length * 2];
            System.arraycopy(oldframes, 0, frames, 0, oldframes.length);
        }
        frames[depth] = name;
        failureDepth = currentDepth;
    }
    
    public void popOnFailure() {
        currentDepth--;
        // failureDepth stays the same and keeps track of this failure
    }
    
    public void popOnSuccess() {
        failureDepth = --currentDepth;
    }
    
    public void popOnExit(boolean success) {
        currentDepth = 0;
        if (success) failureDepth = 0;
    }
    
    public IOAgent getIOAgent() {
        return ioAgent;
    }
    
    public void setIOAgent(IOAgent ioAgent) {
        this.ioAgent = ioAgent;
    }
    
    /**
     *  Returns the current stack trace depth.
     */
    public int getTraceDepth() {
        return failureDepth;
    }
    
   /**
    *  Returns the current stack trace depth.
    * 
    * @param onlyCurrent
    *            true if only the current frames on the stack should be
    *            printed, and not any failed frames.
    */
   public int getTraceDepth(boolean onlyCurrent) {
        return onlyCurrent ? currentDepth : failureDepth;
    }
    
    /**
     * Returns the current stack trace.
     */
    public String[] getTrace() {
        return getTrace(false);
    }
    
    /**
     * Returns the current stack trace.
     * 
     * @param onlyCurrent
     *            true if only the current frames on the stack should be
     *            printed, and not any failed frames.
     */
    public String[] getTrace(boolean onlyCurrent) {
        int depth = onlyCurrent ? currentDepth : failureDepth;
        String[] frames = this.frames; // avoid _some_ race conditions        
        String[] results = new String[depth];
        
        for (int i = 0; i < depth; i++)
            results[results.length - i - 1] = frames[i];
        
        return results;
    }
    
    public void setTrace(String[] trace) {
        currentDepth = min(trace.length, currentDepth);
        failureDepth = trace.length;
        frames = trace;
    }
    
    /**
     * Prints the stack trace to the error output stream of the IOAgent.
     */
    public final void printStackTrace() {
        printStackTrace(false);
    }

    /**
     * Prints the stack trace to the error output stream of the IOAgent.
     * 
     * @param onlyCurrent
     *            true if only the current frames on the stack should be
     *            printed, and not any failed frames.
     */
    public final void printStackTrace(boolean onlyCurrent) {
        PrintStream stream = getIOAgent() == null ? System.err : getIOAgent().getOutputStream(IOAgent.CONST_STDERR);
        printStackTrace(stream, onlyCurrent);
    }

    /**
     * Prints the stack trace to the default error output.
     * 
     * @param onlyCurrent
     *            true if only the current frames on the stack should be
     *            printed, and not any failed frames.
     */
    public void printStackTrace(PrintStream stream, boolean onlyCurrent) {
        int depth = onlyCurrent ? currentDepth : failureDepth;
        String[] frames = this.frames.clone(); // avoid _most_ race conditions (for UncaughtExceptionHandler)
        
        for (int i = 0; i < depth; i++) {
            if (i == MAX_REPORTED_FRAMES - MAX_REPORTED_FRAMES_TAIL) {
                stream.println("...truncated...");
                i = Math.max(i + 1, depth - MAX_REPORTED_FRAMES_TAIL);
            }
            stream.println("\t" + frames[i]);
        }
    }
}
