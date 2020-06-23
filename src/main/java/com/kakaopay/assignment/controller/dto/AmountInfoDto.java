package com.kakaopay.assignment.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AmountInfoDto {
    private Integer paidAmount;
    private Integer paidVat;


    @Builder
    public AmountInfoDto(Integer paidAmount, Integer paidVat) {
        this.paidAmount = paidAmount;
        this.paidVat = paidVat;
    }

    public static AmountInfoDto of(Integer paidAmount, Integer paidVat) {
        return AmountInfoDto.builder()
            .paidAmount(paidAmount)
            .paidVat(paidVat)
            .build();
    }
}
