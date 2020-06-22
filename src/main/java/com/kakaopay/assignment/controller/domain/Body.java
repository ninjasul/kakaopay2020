package com.kakaopay.assignment.controller.domain;

import com.kakaopay.assignment.controller.domain.field.FieldInfo;

import static com.kakaopay.assignment.controller.domain.field.FieldMeta.*;

public class Body {
    @FieldInfo(meta = NUMBER_LEFT, maxSize = 20)
    private String cardNumber;

    @FieldInfo(meta = NUMBER_RIGHT_WITH_ZERO, maxSize = 2)
    private String installmentMonths;

    @FieldInfo(meta = NUMBER_LEFT, maxSize = 4)
    private String validPeriod;

    @FieldInfo(meta = NUMBER_LEFT, maxSize = 3)
    private String cvc;

    @FieldInfo(meta = NUMBER_RIGHT, maxSize = 10)
    private String paymentAmount;

    @FieldInfo(meta = NUMBER_RIGHT_WITH_ZERO, maxSize = 10)
    private String vat;

    @FieldInfo(meta = STRING_LEFT, maxSize = 20)
    private String orgMgmtNo;

    @FieldInfo(meta = STRING_LEFT, maxSize = 300)
    private String encryptedCardInfo;

    @FieldInfo(meta = STRING_LEFT, maxSize = 47)
    private String reserved;

    public Body(
        String cardNumber,
        String installmentMonths,
        String validPeriod,
        String cvc,
        String paymentAmount,
        String vat,
        String orgMgmtNo
    ) {
        this.cardNumber = cardNumber;
        this.installmentMonths = installmentMonths;
        this.validPeriod = validPeriod;
        this.cvc = cvc;
        this.paymentAmount = paymentAmount;
        this.vat = vat;
        this.orgMgmtNo = orgMgmtNo;
    }
}
