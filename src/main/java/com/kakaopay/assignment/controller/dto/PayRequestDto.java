package com.kakaopay.assignment.controller.dto;

import com.kakaopay.util.StringUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class PayRequestDto {

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

    @NotEmpty
    @Min(0)
    @Max(12)
    @Pattern(regexp = "^[0-9]{1,2}$")
    private String installmentMonths;

    @NotEmpty
    @Min(100)
    @Max(1000000000)
    @Pattern(regexp = "^[0-9]{3,10}$")
    private String paymentAmount;

    @Min(0)
    @Max(1000000000)
    @Pattern(regexp = "^[0-9]{1,10}$")
    private String vat;

    @Builder
    public PayRequestDto(
        String cardNo,
        String validPeriod,
        String cvc,
        String installmentMonths,
        String paymentAmount,
        String vat) {
        this.cardNo = cardNo;
        this.validPeriod = validPeriod;
        this.cvc = cvc;
        this.installmentMonths = installmentMonths;
        this.paymentAmount = paymentAmount;
        this.vat = StringUtils.getOrDefault(vat, null);
    }
}
