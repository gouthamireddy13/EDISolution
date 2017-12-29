package com.abc.tpi.common.exceptions;

public class TpiRepositoryException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    public TpiRepositoryException(String message) {
        super(message);
    }
    
    public TpiRepositoryException(String message, Throwable originalEx)
    {
    	super(message,originalEx);
    }

}
