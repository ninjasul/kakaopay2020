package com.kakaopay.assignment.controller.domain;

import com.kakaopay.assignment.controller.domain.field.DataType;
import com.kakaopay.assignment.controller.domain.field.FieldInfo;
import com.kakaopay.util.FieldUtil;

import static com.kakaopay.assignment.controller.domain.field.FieldMeta.NUMBER_RIGHT;
import static com.kakaopay.assignment.controller.domain.field.FieldMeta.STRING_LEFT;

public class Header {
    @FieldInfo(meta = NUMBER_RIGHT, maxSize = 4)
    private int dataLength;

    @FieldInfo(meta = STRING_LEFT, maxSize = 10)
    private DataType dataType;

    @FieldInfo(meta = STRING_LEFT, maxSize = 20)
    private String managementNumber;

    public Header(int dataLength, DataType dataType, String managementNumber) {
        this.dataLength = dataLength;
        this.dataType = dataType;
        this.managementNumber = managementNumber;
    }

    @Override
    public String toString() {
        return FieldUtil.getValues(this, this.getClass().getDeclaredFields());
    }
}
