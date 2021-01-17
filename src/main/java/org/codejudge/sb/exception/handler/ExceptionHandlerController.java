package org.codejudge.sb.exception.handler;

import org.codejudge.sb.entity.LeadEntity;
import org.codejudge.sb.error.messaging.ErrorMessage;
import org.codejudge.sb.exception.LeadException;
import org.codejudge.sb.exception.MessageException;
import org.codejudge.sb.model.LeadStatus;
import org.codejudge.sb.service.impl.LeadServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

	@ExceptionHandler(LeadException.class)
	protected ResponseEntity<Object> handleLeadNotFoundException(LeadException ex, WebRequest req) {
		if (ex.getMessage().isEmpty()) {
			logger.info("[handleLeadNotFoundException] : statusCode : " + ex.getStatusCode());
			return new ResponseEntity<>(new LeadEntity(), new HttpHeaders(), ex.getStatusCode());
		}
		ErrorMessage body = new ErrorMessage(LeadStatus.failure, ex.getMessage());
		logger.info("[handleLeadNotFoundException] : statusCode : " + ex.getStatusCode());
		return new ResponseEntity<>(body, new HttpHeaders(), ex.getStatusCode());
	}

	@ExceptionHandler(MessageException.class)
	protected ResponseEntity<Object> handleMyException(MessageException ex, WebRequest req) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, req);
	}
}
