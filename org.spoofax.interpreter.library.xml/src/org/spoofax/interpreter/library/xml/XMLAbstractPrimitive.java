package org.spoofax.interpreter.library.xml;

import org.spoofax.interpreter.library.AbstractPrimitive;

/**
 * @author Tobi Vollebregt
 */
public abstract class XMLAbstractPrimitive extends AbstractPrimitive {

	protected final XMLLibrary library;

	public XMLAbstractPrimitive(XMLLibrary library, String name, int svars, int tvars) {
		super(name, svars, tvars);
		this.library = library;
	}

}
