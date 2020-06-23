package com.kakaopay.assignment.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class PayFailResponseDto {
    private String code;
    private String message;

    @Builder
    public PayFailResponseDto(HttpStatus httpStatus) {
        this.code = String.valueOf(httpStatus.value());
        this.message = httpStatus.getReasonPhrase();
    }

    public PayFailResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
