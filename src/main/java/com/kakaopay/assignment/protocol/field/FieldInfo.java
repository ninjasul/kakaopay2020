package com.kakaopay.assignment.protocol.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldInfo {
    FieldMeta meta() default FieldMeta.NUMBER_RIGHT;

    int maxSize() default 0;
}
