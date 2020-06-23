package com.kakaopay.assignment.util;

import com.kakaopay.assignment.domain.field.FieldInfo;
import com.kakaopay.assignment.domain.field.FieldMeta;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.kakaopay.assignment.domain.field.FieldAlignment.LEFT;

@Slf4j
public class FieldUtil {
    public static String getValues(Object instance, Field[] declaredFields) {
        if (Objects.isNull(instance) ||
            ArrayUtils.isEmpty(declaredFields)) {
            return "";
        }

        return Arrays.stream(declaredFields)
            .filter(field -> field.isAnnotationPresent(FieldInfo.class))
            .map(FieldUtil::setAccessible)
            .map(field -> getValue(instance, field, field.getAnnotation(FieldInfo.class)))
            .map(String::valueOf)
            .collect(Collectors.joining());
    }

    private static Field setAccessible(Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }

        return field;
    }

    private static String getValue(Object instance, Field field, FieldInfo fieldInfo) {
        try {
            Object value = field.get(instance);
            FieldMeta meta = fieldInfo.meta();

            if (LEFT.equals(meta.getAlignment())) {
                return StringUtils.leftPad(String.valueOf(value), fieldInfo.maxSize(), meta.getPadChar());
            }

            return StringUtils.rightPad(String.valueOf(value), fieldInfo.maxSize(), meta.getPadChar());
        }
        catch (IllegalAccessException e) {
            log.error(e.getMessage());
            return "";
        }
    }

    public static int getLength(Object instance, Field field) {
        try {
            Object value = field.get(instance);
            return String.valueOf(value).length();
        }
        catch (IllegalAccessException e) {
            log.error(e.getMessage());
            return 0;
        }
    }
}
