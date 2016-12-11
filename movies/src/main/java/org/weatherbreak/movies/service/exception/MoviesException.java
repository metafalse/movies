package org.weatherbreak.movies.service.exception;

@SuppressWarnings("serial")
public class MoviesException extends RuntimeException {
	private ErrorCode errorCode;

	public MoviesException(ErrorCode code, String message, Throwable throwable) {
		super(message, throwable);
		this.errorCode = code;
	}

	public MoviesException(ErrorCode code, String message) {
		super(message);
		this.errorCode = code;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

}
