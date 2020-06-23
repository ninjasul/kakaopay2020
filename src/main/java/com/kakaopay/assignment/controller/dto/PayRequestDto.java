package com.kakaopay.assignment.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class PayRequestDto implements VatCalculable {

    @NotEmpty
    @Min(0)
    @Pattern(regexp ="^[0-9]{10,16}$")
    private String cardNo;

    @NotEmpty
    @Min(100)
    @Max(1299)
    @Pattern(regexp ="^[0-1][0-9]{3}$")
    private String validPeriod;

    @NotEmpty
    @Pattern(regexp ="^[0-9]{3}$")
    private String cvc;

    @NotNull
    @Min(0)
    @Max(12)
    private Integer installmentMonths;

    @NotNull
    @Min(100)
    @Max(1000000000)
    private Integer paymentAmount;

    @Min(0)
    @Max(1000000000)
    private Integer vat;

    @Builder
    public PayRequestDto(
        String cardNo,
        String validPeriod,
        String cvc,
        Integer installmentMonths,
        Integer paymentAmount,
        Integer vat) {
        this.cardNo = cardNo;
        this.validPeriod = validPeriod;
        this.cvc = cvc;
        this.installmentMonths = installmentMonths;
        this.paymentAmount = paymentAmount;
        this.vat = getCalculatedVat(paymentAmount, vat);
    }
}
