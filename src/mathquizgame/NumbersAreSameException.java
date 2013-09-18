/*
 * Copyright (c) 2013 Jasper Reddin
 * All rights reserved
 */
package mathquizgame;

public class NumbersAreSameException extends Exception {

	/**
	 * Creates a new instance of
	 * <code>NumbersAreSameException</code> without detail message.
	 */
	public NumbersAreSameException() {
	}

	/**
	 * Constructs an instance of
	 * <code>NumbersAreSameException</code> with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public NumbersAreSameException(String msg) {
		super(msg);
	}
}
