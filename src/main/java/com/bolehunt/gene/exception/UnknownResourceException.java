package com.bolehunt.gene.exception;

public class UnknownResourceException extends RuntimeException {

	private static final long serialVersionUID = -2342576785616578890L;

	public UnknownResourceException(String msg) {
        super(msg);
    }
}
