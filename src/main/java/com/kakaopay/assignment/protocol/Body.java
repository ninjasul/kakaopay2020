package com.kakaopay.assignment.protocol;

import com.kakaopay.assignment.controller.dto.CancelRequestDto;
import com.kakaopay.assignment.controller.dto.PayRequestDto;
import com.kakaopay.assignment.protocol.field.FieldInfo;
import com.kakaopay.assignment.entity.PaymentHistory;
import com.kakaopay.assignment.protocol.field.FieldMeta;
import com.kakaopay.assignment.util.Aes256Cipher;
import com.kakaopay.assignment.util.FieldUtil;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Body implements LengthGettable {
    @FieldInfo(meta = FieldMeta.NUMBER_LEFT, maxSize = 20)
    private String cardNo;

    @FieldInfo(meta = FieldMeta.NUMBER_RIGHT_WITH_ZERO, maxSize = 2)
    private int installmentMonths;

    @FieldInfo(meta = FieldMeta.NUMBER_LEFT, maxSize = 4)
    private String validPeriod;

    @FieldInfo(meta = FieldMeta.NUMBER_LEFT, maxSize = 3)
    private String cvc;

    @FieldInfo(meta = FieldMeta.NUMBER_RIGHT, maxSize = 10)
    private Integer amount;

    @FieldInfo(meta = FieldMeta.NUMBER_RIGHT_WITH_ZERO, maxSize = 10)
    private Integer vat;

    @FieldInfo(meta = FieldMeta.STRING_LEFT, maxSize = 20)
    private String orgMgmtNo;

    @FieldInfo(meta = FieldMeta.STRING_LEFT, maxSize = 300)
    private String encryptedCardInfo;

    @FieldInfo(meta = FieldMeta.STRING_LEFT, maxSize = 47)
    @LengthExcluded
    private String reserved;

    @Builder
    public Body(
        String cardNo,
        int installmentMonths,
        String validPeriod,
        String cvc,
        Integer amount,
        Integer vat,
        String orgMgmtNo
    ) {
        this.cardNo = cardNo;
        this.installmentMonths = installmentMonths;
        this.validPeriod = validPeriod;
        this.cvc = cvc;
        this.amount = amount;
        this.vat = vat;
        this.orgMgmtNo = orgMgmtNo;
        this.encryptedCardInfo = Aes256Cipher.encryptCardInfo(cardNo, validPeriod, cvc);
        this.reserved = "";
    }

    public static Body from(PayRequestDto dto) {
        return Body.builder()
            .cardNo(dto.getCardNo())
            .installmentMonths(dto.getInstallmentMonths())
            .validPeriod(dto.getValidPeriod())
            .cvc(dto.getCvc())
            .amount(dto.getPaymentAmount())
            .vat(dto.getVat())
            .orgMgmtNo("")
            .build();
    }

    public static Body from(CancelRequestDto cancelRequestDto, PaymentHistory paymentHistory) {
        return Body.builder()
            .cardNo(paymentHistory.getCardNo())
            .installmentMonths(paymentHistory.getInstallmentMonths())
            .validPeriod(paymentHistory.getValidPeriod())
            .cvc(paymentHistory.getCvc())
            .amount(cancelRequestDto.getCancelAmount())
            .vat(cancelRequestDto.getVat())
            .orgMgmtNo(paymentHistory.getMgmtNo())
            .build();
    }

    @Override
    public String toString() {
        return FieldUtil.getValues(this, this.getClass().getDeclaredFields());
    }
}
