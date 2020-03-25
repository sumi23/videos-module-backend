package com.example.video.exception;

public class DBException extends RuntimeException
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DBException(String message) {
	       super(message);
	
}

public DBException(String message,Throwable t) {
	super(message,t);
}



}