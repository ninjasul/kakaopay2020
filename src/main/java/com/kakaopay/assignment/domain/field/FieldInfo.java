package com.kakaopay.assignment.domain.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.kakaopay.assignment.domain.field.FieldMeta.NUMBER_RIGHT;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldInfo {
    FieldMeta meta() default NUMBER_RIGHT;

    int maxSize() default 0;
}
