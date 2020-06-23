package com.kakaopay.assignment.controller;

import com.kakaopay.assignment.controller.dto.PayFailResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
@Slf4j
public class PaymentControllerAdvice {
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class, EntityNotFoundException.class})
    public ResponseEntity<PayFailResponseDto> handleBadRequest() {
        PayFailResponseDto responseBody = PayFailResponseDto.builder()
            .httpStatus(HttpStatus.BAD_REQUEST).build();

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
}
