package com.kakaopay.assignment.domain;

import com.kakaopay.assignment.domain.field.FieldInfo;
import com.kakaopay.assignment.domain.field.RequestType;
import com.kakaopay.assignment.util.FieldUtil;
import com.kakaopay.assignment.util.ManagementNumberUtil;
import lombok.Builder;
import lombok.Getter;

import static com.kakaopay.assignment.domain.field.FieldMeta.NUMBER_RIGHT;
import static com.kakaopay.assignment.domain.field.FieldMeta.STRING_LEFT;

public class Header implements LengthGettable {
    @FieldInfo(meta = NUMBER_RIGHT, maxSize = 4)
    @LengthExcluded
    private int length;

    @Getter
    @FieldInfo(meta = STRING_LEFT, maxSize = 10)
    private RequestType requestType;

    @Getter
    @FieldInfo(meta = STRING_LEFT, maxSize = 20)
    private String mgmtNo;

    public static Header from(RequestType requestType) {
        return Header.builder()
            .requestType(requestType)
            .mgmtNo(ManagementNumberUtil.generate())
            .build();
    }

    public static Header newCancelHeader() {
        return Header.builder()
            .requestType(RequestType.CANCEL)
            .mgmtNo(ManagementNumberUtil.generate())
            .build();
    }

    public Header length(int length) {
        this.length = length;
        return this;
    }

    @Builder
    public Header(int length, RequestType requestType, String mgmtNo) {
        this.length = length;
        this.requestType = requestType;
        this.mgmtNo = mgmtNo;
    }

    @Override
    public String toString() {
        return FieldUtil.getValues(this, this.getClass().getDeclaredFields());
    }
}
