package com.kakaopay.assignment.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PayResponseDto extends PayFailResponseDto {
    private String managementNumber;
    private String header;
    private String body;

    public PayResponseDto(String managementNumber, String header, String body) {
        this.managementNumber = managementNumber;
        this.header = header;
        this.body = body;
    }
}
