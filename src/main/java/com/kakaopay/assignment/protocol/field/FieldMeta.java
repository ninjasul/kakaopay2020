package com.kakaopay.assignment.protocol.field;

import lombok.Getter;

@Getter
public enum FieldMeta {
    NUMBER_RIGHT(FieldType.NUMBER, FieldAlignment.RIGHT, ' '),
    NUMBER_RIGHT_WITH_ZERO(FieldType.NUMBER, FieldAlignment.RIGHT, '0'),
    NUMBER_LEFT(FieldType.NUMBER, FieldAlignment.LEFT, ' '),
    STRING_LEFT(FieldType.STRING, FieldAlignment.LEFT, ' '),
    ;

    private final FieldType type;
    private final FieldAlignment alignment;
    private final char padChar;

    FieldMeta(FieldType type, FieldAlignment alignment, char padChar) {
        this.type = type;
        this.alignment = alignment;
        this.padChar = padChar;
    }
}
