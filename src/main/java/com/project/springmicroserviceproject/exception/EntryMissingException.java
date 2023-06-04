package com.project.springmicroserviceproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="No such record")
public class EntryMissingException extends RuntimeException {


    public EntryMissingException(String exception){
        super(exception);
    }

    
}
