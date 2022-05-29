package com.ripple.assignment.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VmResourceNotFoundException extends RuntimeException {

    public VmResourceNotFoundException(String message) {
        super(message);
    }
}
