package com.kakaopay.assignment.controller.domain.field;

import lombok.Getter;

import static com.kakaopay.assignment.controller.domain.field.FieldAlignment.LEFT;
import static com.kakaopay.assignment.controller.domain.field.FieldAlignment.RIGHT;
import static com.kakaopay.assignment.controller.domain.field.FieldType.NUMBER;
import static com.kakaopay.assignment.controller.domain.field.FieldType.STRING;

@Getter
public enum FieldMeta {
    NUMBER_RIGHT(NUMBER, RIGHT, ' '),
    NUMBER_RIGHT_WITH_ZERO(NUMBER, RIGHT, '0'),
    NUMBER_LEFT(NUMBER, LEFT, ' '),
    STRING_LEFT(STRING, LEFT, ' '),
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
