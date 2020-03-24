package com.otto.spacetravel.exception;

/*
 * Custom exception used when given route doesn't exist.
 */	
public class RouteNotFoundException extends Exception {
	private static final long serialVersionUID = -7364727744256138314L;

	public RouteNotFoundException() {
		super();
	}

	public RouteNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RouteNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public RouteNotFoundException(String message) {
		super(message);
	}

	public RouteNotFoundException(Throwable cause) {
		super(cause);
	}
}
