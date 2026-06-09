package com.example.arena_booker.Exception;


import com.example.arena_booker.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex){
        ErrorResponseDto errorDto = new ErrorResponseDto(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReservationConflictException.class)
    public ResponseEntity<ErrorResponseDto> handleReservationConflictException(ReservationConflictException ex){
        ErrorResponseDto errorDto = new ErrorResponseDto(LocalDateTime.now(), HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
    }

}
