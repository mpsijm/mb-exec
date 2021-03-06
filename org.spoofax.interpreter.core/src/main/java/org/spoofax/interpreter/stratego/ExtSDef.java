/*
 * Created on 07.aug.2005
 *
 * Copyright (c) 2004, Karl Trygve Kalleberg <karltk near strategoxt.org>
 * 
 * Licensed under the GNU Lesser General Public License, v2.1
 */
package org.spoofax.interpreter.stratego;

import org.spoofax.interpreter.core.VarScope;

public class ExtSDef extends SDefT {

    public ExtSDef(String name, SVar[] realsvars, String[] realtvars,
            VarScope scope) {
        super(name, realsvars, realtvars, null, scope);
    }

    @Override
    public String toString() {
        return "ExtSDef(\"" + getName() + "\")";
    }

    @Override
    public Strategy getBody() {
        return null;
    }
}
