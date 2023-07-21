package com.dia.delivery.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

 @RestControllerAdvice
    public class GlobalControllerAdvice {

        @ExceptionHandler({IllegalArgumentException.class})
        public ResponseEntity<ApiResponseDto> handleException(IllegalArgumentException ex) {
            ApiResponseDto apiResponseDto = new ApiResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(
                    // HTTP body
                    apiResponseDto,
                    // HTTP status code
                    HttpStatus.BAD_REQUEST
            );
        }


         @ExceptionHandler({NullPointerException.class})
         public ResponseEntity<ApiResponseDto> nullPointerExceptionHandler(NullPointerException ex) {
             ApiResponseDto apiResponseDto = new ApiResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
             return new ResponseEntity<>(
                     // HTTP body
                     apiResponseDto,
                     // HTTP status code
                     HttpStatus.NOT_FOUND
             );
         }}




