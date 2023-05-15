package com.project.springmicroserviceproject.exception;

public class EntryMissingException extends RuntimeException {

    public EntryMissingException(String exception){
        super(exception);
    }
    
}
