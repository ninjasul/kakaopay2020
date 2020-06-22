package com.kakaopay.assignment.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class PayFailResponseDto {
    protected String code;
    protected String message;

    @Builder
    public PayFailResponseDto(HttpStatus httpStatus) {
        this.code = String.valueOf(httpStatus.value());
        this.message = httpStatus.getReasonPhrase();
    }

    public PayFailResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PayFailResponseDto that = (PayFailResponseDto) o;
        return Objects.equals(code, that.code) &&
            Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message);
    }
}
