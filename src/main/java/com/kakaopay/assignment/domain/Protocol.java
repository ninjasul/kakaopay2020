package com.kakaopay.assignment.domain;

import com.kakaopay.assignment.controller.dto.CancelRequestDto;
import com.kakaopay.assignment.controller.dto.PayRequestDto;
import com.kakaopay.assignment.domain.field.RequestType;
import com.kakaopay.assignment.entity.PaymentHistory;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Protocol {
    private final Header header;
    private final Body body;

    @Builder
    public Protocol(Header header, Body body) {
        if (Objects.isNull(header) || Objects.isNull(body)) {
            throw new IllegalArgumentException();
        }

        this.header = header;
        this.body = body;
    }

    public static Protocol from(PayRequestDto payRequestDto) {
        Header header = Header.from(RequestType.PAYMENT);
        Body body = Body.from(payRequestDto);
        header.length(header.getLength() + body.getLength());

        return Protocol.builder()
            .header(header)
            .body(body)
            .build();
    }

    public static Protocol from(CancelRequestDto cancelRequestDto, PaymentHistory paymentHistory) {
        Header header = Header.newCancelHeader();
        Body body = Body.from(cancelRequestDto, paymentHistory);
        header.length(header.getLength() + body.getLength());

        return Protocol.builder()
            .header(header)
            .body(body)
            .build();
    }


    public String getMgmtNo() {
        return getHeader().getMgmtNo();
    }

    public String getCardNo() {
        return getBody().getCardNo();
    }

    public String getValidPeriod() {
        return getBody().getValidPeriod();
    }

    public String getCvc() {
        return getBody().getCvc();
    }

    public Integer getInstallmentMonths() {
        return getBody().getInstallmentMonths();
    }

    public Integer getAmount() {
        return getBody().getAmount();
    }

    public Integer getVat() {
        return getBody().getVat();
    }

    @Override
    public String toString() {
        return header.toString() + body.toString();
    }
}
