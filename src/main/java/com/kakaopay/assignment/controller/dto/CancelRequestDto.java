package com.kakaopay.assignment.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class CancelRequestDto {
    @NotEmpty
    @Size(min = 20, max = 20)
    private String mgmtNo;

    @NotEmpty
    @Min(1)
    @Max(1000000000)
    @Pattern(regexp = "^[0-9]{1,10}$")
    private Integer cancelAmount;

    @Min(1)
    @Max(1000000000)
    @Pattern(regexp = "^[0-9]{1,10}$")
    private Integer vat;

    @Builder
    public CancelRequestDto(
        String mgmtNo,
        Integer cancelAmount,
        Integer vat) {
        this.mgmtNo = mgmtNo;
        this.cancelAmount = cancelAmount;
        this.vat = Objects.isNull(vat) ? 0 : vat;
    }
}
