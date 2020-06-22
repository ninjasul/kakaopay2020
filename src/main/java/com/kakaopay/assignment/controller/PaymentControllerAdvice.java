package com.kakaopay.assignment.controller;

import com.kakaopay.assignment.controller.dto.PayFailResponseDto;
import com.kakaopay.assignment.controller.dto.PayRequestDto;
import com.kakaopay.assignment.controller.dto.PayResponseDto;
import com.kakaopay.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestControllerAdvice
@Slf4j
public class PaymentControllerAdvice {

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<PayFailResponseDto> handleIllegalArgumentException() {
        PayFailResponseDto responseBody = PayFailResponseDto.builder()
            .httpStatus(HttpStatus.BAD_REQUEST).build();

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
}
