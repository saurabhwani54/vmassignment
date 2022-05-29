package com.ripple.assignment.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class VmAuthException extends RuntimeException{

	public VmAuthException(String message) {
		super(message);
	}
}
