package com.chinakrit.crm.exception;

import java.io.Serial;

public class HttpException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;
	
	public HttpException(String message) {
		super(message);
	}
}
