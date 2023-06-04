package com.project.springmicroserviceproject.exception.advice;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.project.springmicroserviceproject.exception.EntryMissingException;
import com.project.springmicroserviceproject.exception.InvalidFieldException;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    // @Autowired
    // private Validator validator;

    // @InitBinder
    // public void initBinder(WebDataBinder binder) {
    //     binder.setValidator(validator);
    // }
    
        
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception exception,
                                                            WebRequest request) {
        ErrorDetails exceptionResponse = new ErrorDetails(LocalDateTime.now(), exception.getMessage());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidFieldException.class)
    protected ResponseEntity<Object> handleMethodArgumentInvalid(InvalidFieldException exception,
                                                                                    WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),exception.getMessage());
                                                             
       // ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
       //                                                 "Request invalid",exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }                                                            

                                                
    @ExceptionHandler(EntryMissingException.class)
    protected ResponseEntity<Object> handleItemNotFoundException(EntryMissingException ex, WebRequest request){

       // ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
       ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),ex.getMessage());
       
       return new ResponseEntity<Object>(errorDetails,HttpStatus.NOT_FOUND);
    }

    // public HttpHeaders createHeaders(){

    //     HttpHeaders respHeader = new HttpHeaders();
    //     respHeader.set("Accept", "application/json");   
    //     return respHeader;
    // }
    
}
