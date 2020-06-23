package com.kakaopay.assignment.controller.dto;

import java.util.Objects;

public interface VatCalculable {
    default Integer getCalculatedVat(Integer amount, Integer vat) {
        if (Objects.isNull(vat)) {
            return Math.round(amount/11);
        }

        return vat;
    }
}
