package com.kakaopay.assignment.entity;

import com.kakaopay.assignment.controller.dto.CancelRequestDto;
import com.kakaopay.assignment.protocol.Body;
import com.kakaopay.assignment.protocol.Header;
import com.kakaopay.assignment.protocol.Protocol;
import com.kakaopay.assignment.protocol.field.PaymentStatus;
import com.kakaopay.assignment.protocol.field.RequestType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "PAYMENT_HISTORY",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"id", "mgmtNo"}
        )
    },
    indexes = {
        @Index(name = "IDX_PAYMENT_HISTORY", columnList = "mgmtNo"),
    }
)
@NoArgsConstructor
@Getter
@Slf4j
public class PaymentHistory {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 20)
    private String mgmtNo;

    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    @Column(nullable = false, length = 16)
    private String cardNo;

    @Column(nullable = false, length = 4)
    private String validPeriod;

    @Column(nullable = false, length = 3)
    private String cvc;

    @Column(nullable = false)
    private Integer installmentMonths;

    private Integer paidAmount;

    private Integer cancelledAmount;

    private Integer paidVat;

    private Integer cancelledVat;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(nullable = false, length = 450)
    private String data;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Transient
    private Header header;

    @Transient
    private Body body;

    public PaymentHistory(
        String mgmtNo,
        String cardNo,
        String validPeriod,
        String cvc,
        Integer installmentMonths,
        RequestType requestType,
        Integer amount,
        Integer vat,
        String data
    ) {
        this.mgmtNo = mgmtNo;
        this.cardNo = cardNo;
        this.validPeriod = validPeriod;
        this.cvc = cvc;
        this.installmentMonths = installmentMonths;
        this.requestType = requestType;

        if (RequestType.PAYMENT.equals(requestType)) {
            this.paidAmount = amount;
            this.paidVat = vat;
            this.cancelledAmount = 0;
            this.cancelledVat = 0;
            this.status = PaymentStatus.PAID;
        }
        else {
            this.paidAmount = 0;
            this.paidVat = 0;
            this.cancelledAmount = amount;
            this.cancelledVat = vat;
            this.status = PaymentStatus.CANCEL_REQUESTED;
        }

        this.data = data;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static PaymentHistory from(Protocol protocol, RequestType requestType) {
        return new PaymentHistory(
            protocol.getMgmtNo(),
            protocol.getCardNo(),
            protocol.getValidPeriod(),
            protocol.getCvc(),
            protocol.getInstallmentMonths(),
            requestType,
            protocol.getAmount(),
            protocol.getVat(),
            protocol.toString()
        );
    }

    public boolean isCompletelyCancelled() {
        return PaymentStatus.COMPLETELY_CANCELLED.equals(status) ||
            cancelledAmount >= paidAmount;
    }

    public boolean canCancel(CancelRequestDto cancelRequestDto) {
        return canCancelAmount(cancelRequestDto.getCancelAmount()) && canCancelVat(cancelRequestDto.getCancelAmount(), cancelRequestDto.getVat());
    }

    private boolean canCancelAmount(Integer cancelRequestAmount) {
        return getRemainingAmount() > 0 && cancelRequestAmount <= getRemainingAmount();
    }

    private boolean canCancelVat(Integer cancelRequestAmount, Integer cancelRequestVat) {
        if (cancelRequestAmount == getRemainingAmount() &&
            cancelRequestVat < getRemainingVat()) {
            return false;
        }

        return getRemainingVat() >= 0 && cancelRequestVat <= getRemainingVat();
    }

    public void cancel(CancelRequestDto cancelRequestDto) {
        cancelledAmount += Math.min(cancelRequestDto.getCancelAmount(), getRemainingAmount());
        cancelledVat += Math.min(cancelRequestDto.getVat(), getRemainingVat());

        if (cancelledAmount.equals(paidAmount)) {
            status = PaymentStatus.COMPLETELY_CANCELLED;
        }
        else {
            status = PaymentStatus.PARTIALLY_CANCELLED;
        }

        updatedAt = LocalDateTime.now();
    }

    public int getRemainingAmount() {
        return paidAmount - cancelledAmount;
    }

    public int getRemainingVat() {
        return paidVat - cancelledVat;
    }
}
