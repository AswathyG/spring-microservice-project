package com.project.springmicroserviceproject.exception.advice;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorDetails {
        private LocalDateTime timestamp;
        private String message;
        //private String details;
  
    public ErrorDetails(LocalDateTime timestamp, String message) {
     // super();
      this.timestamp = timestamp;
      this.message = message;
     // this.details = details;
    }
}
  