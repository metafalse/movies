package org.weatherbreak.movies.service.exception;

@SuppressWarnings("serial")
public class InvalidFieldException extends MoviesException {

	public InvalidFieldException(String message, Throwable throwable) {
		super(ErrorCode.INVALID_FIELD, message, throwable);
	}
	
	public InvalidFieldException(String message) {
		super(ErrorCode.INVALID_FIELD, message);
	}

}
