package com.project.springmicroserviceproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Invalid request")
public class InvalidFieldException extends RuntimeException {

    private BindingResult result;

    public InvalidFieldException(BindingResult result, String exception) {
        super(exception);
        this.result=result;
    }
   
}