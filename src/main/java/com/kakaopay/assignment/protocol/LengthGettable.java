package com.kakaopay.assignment.protocol;

import com.kakaopay.assignment.util.FieldUtil;

import java.lang.reflect.Field;
import java.util.Arrays;

public interface LengthGettable {
    default int getLength() {
        return Arrays.stream(this.getClass().getDeclaredFields())
            .filter(field -> !isExcluded(field))
            .map(this::setAccessible)
            .mapToInt(field -> FieldUtil.getLength(this, field))
            .sum();
    }

    default boolean isExcluded(Field field) {
        return field.isAnnotationPresent(LengthExcluded.class);
    }

    default Field setAccessible(Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }

        return field;
    }
}
