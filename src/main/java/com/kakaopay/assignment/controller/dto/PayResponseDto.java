package com.kakaopay.assignment.controller.dto;

import com.kakaopay.assignment.entity.PaymentHistory;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PayResponseDto {
    private final String mgmtNo;
    private final String paymentData;

    @Builder
    public PayResponseDto(String mgmtNo, String paymentData) {
        this.mgmtNo = mgmtNo;
        this.paymentData = paymentData;
    }

    public static PayResponseDto from(PaymentHistory paymentHistory) {
        return PayResponseDto.builder()
            .mgmtNo(paymentHistory.getMgmtNo())
            .paymentData(paymentHistory.getData())
            .build();
    }
}
