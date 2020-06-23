package com.kakaopay.assignment.entity;

import com.kakaopay.assignment.controller.dto.CancelRequestDto;
import com.kakaopay.assignment.domain.Body;
import com.kakaopay.assignment.domain.Header;
import com.kakaopay.assignment.domain.field.PaymentStatus;
import com.kakaopay.assignment.domain.field.RequestType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name="PAYMENT_HISTORY",
    uniqueConstraints={
        @UniqueConstraint(
            columnNames = {"id", "mgmtNo"}
        )
    },
    indexes={
        @Index(name="IDX_PAYMENT_HISTORY", columnList="mgmtNo"),
    }
)
@NoArgsConstructor
@Getter
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

    @Column(nullable = false)
    private Integer paidAmount;

    private Integer cancelledAmount;

    @Column(nullable = false)
    private Integer paidVat;

    private Integer cancelledVat;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(nullable = false, length = 450)
    private String data;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Transient
    private Header header;

    @Transient
    private Body body;

    public static PaymentHistory from(Body body, Header header) {
        return new PaymentHistory(
            header.getMgmtNo(),
            body.getCardNo(),
            body.getValidPeriod(),
            body.getCvc(),
            body.getInstallmentMonths(),
            body.getAmount(),
            0,
            body.getVat(),
            0,
            PaymentStatus.PAYMENT,
            header.toString() + body.toString()
        );
    }

    public PaymentHistory(
        String mgmtNo,
        String cardNo,
        String validPeriod,
        String cvc,
        Integer installmentMonths,
        Integer paidAmount,
        Integer cancelledAmount,
        Integer paidVat,
        Integer cancelledVat,
        PaymentStatus status,
        String data
    ) {
        this.mgmtNo = mgmtNo;
        this.cardNo = cardNo;
        this.validPeriod = validPeriod;
        this.cvc = cvc;
        this.installmentMonths = installmentMonths;
        this.paidAmount = paidAmount;
        this.cancelledAmount = cancelledAmount;
        this.paidVat = paidVat;
        this.cancelledVat = cancelledVat;
        this.status = status;
        this.data = data;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isCancelled() {
        return PaymentStatus.CANCEL.equals(status) ||
            cancelledAmount >= paidAmount;
    }

    public boolean canCancel(CancelRequestDto cancelRequestDto) {
        return canCancelAmount(cancelRequestDto.getCancelAmount()) && canCancelVat(cancelRequestDto.getVat());
    }

    private boolean canCancelAmount(Integer cancelRequestAmount) {
        return getRemainingAmount() > 0 && cancelRequestAmount <= getRemainingAmount();
    }

    private boolean canCancelVat(Integer cancelRequestVat) {
        return getRemainingVat() > 0 && cancelRequestVat <= getRemainingVat();
    }

    public void cancel(CancelRequestDto cancelRequestDto) {
        cancelledAmount += Math.min(cancelRequestDto.getCancelAmount(), getRemainingAmount());
        cancelledVat += Math.min(cancelRequestDto.getVat(), getRemainingVat());

        if (cancelledAmount.equals(paidAmount)) {
            status = PaymentStatus.CANCEL;
        }
        else {
            status = PaymentStatus.PARTIAL_CANCEL;
        }
    }

    private int getRemainingAmount() {
        return paidAmount - cancelledAmount;
    }

    private int getRemainingVat() {
        return paidVat - cancelledVat;
    }
}
