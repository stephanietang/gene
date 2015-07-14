package com.bolehunt.gene.exception;

/**
 * Simulated business-logic exception indicating a desired business entity or record cannot be found.
 */
public class UnknownResourceException extends RuntimeException {

	private static final long serialVersionUID = -2342576785616578890L;

	public UnknownResourceException(String msg) {
        super(msg);
    }
}
