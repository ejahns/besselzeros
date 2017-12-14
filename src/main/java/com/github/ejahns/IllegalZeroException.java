package com.github.ejahns;

/**
 * Thrown to indicate that a desired zero was smaller than the first zero of <code>J_0</code>.
 */
public class IllegalZeroException extends IllegalArgumentException {

	/**
	 * Constructs an <code>IllegalZeroException</code> with the default message.
	 */
	public IllegalZeroException() {
		super("the specified zero cannot be smaller than the first zero of J_0");
	}

}
