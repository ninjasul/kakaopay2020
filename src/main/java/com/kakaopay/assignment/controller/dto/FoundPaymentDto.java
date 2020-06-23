package com.kakaopay.assignment.controller.dto;

import com.kakaopay.assignment.entity.PaymentHistory;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FoundPaymentDto {
    private String mgmtNo;
    private CardInfoDto cardInfo;
    private AmountInfoDto amountInfo;

    @Builder
    public FoundPaymentDto(
        String mgmtNo,
        String cardNo,
        String validPeriod,
        String cvc,
        Integer paidAmount,
        Integer paidVat
    ) {
        this.mgmtNo = mgmtNo;
        this.cardInfo = CardInfoDto.of(cardNo, validPeriod, cvc);
        this.amountInfo = AmountInfoDto.of(paidAmount, paidVat);
    }

    public static FoundPaymentDto from(PaymentHistory paymentHistory) {
        return FoundPaymentDto.builder()
            .mgmtNo(paymentHistory.getMgmtNo())
            .cardNo(paymentHistory.getCardNo())
            .validPeriod(paymentHistory.getValidPeriod())
            .cvc(paymentHistory.getCvc())
            .paidAmount(paymentHistory.getPaidAmount())
            .paidVat(paymentHistory.getPaidVat())
            .build();
    }
}
