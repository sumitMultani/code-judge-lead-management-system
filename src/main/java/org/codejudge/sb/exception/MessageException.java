package org.codejudge.sb.exception;

public class MessageException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3181219338368240989L;

	public MessageException(String errorMessage) {
		super(errorMessage);
	}
	
}
