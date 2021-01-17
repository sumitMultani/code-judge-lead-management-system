package org.codejudge.sb.exception;

import org.codejudge.sb.model.LeadRequest;
import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class LeadException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3181219338368240989L;

	public HttpStatus statusCode;
	
	public LeadRequest lead;
	
	public LeadException(String errorMessage, HttpStatus statusCode) {
		super(errorMessage);
		this.statusCode = statusCode;
	}
	
	public LeadException(String errorMessage, LeadRequest lead, HttpStatus statusCode) {
		super(errorMessage);
		this.lead = lead;
		this.statusCode = statusCode;
	}
	
}
