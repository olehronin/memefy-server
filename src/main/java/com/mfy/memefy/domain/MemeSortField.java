package com.mfy.memefy.domain;

import java.util.Arrays;

/**
 * The {@link MemeSortField} enum
 *
 * @author Oleh Ivasiuk
 */
public enum MemeSortField {
    ID("id"),
    NAME("name"),
    LIKES("likes");

    private final String fieldName;

    MemeSortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static MemeSortField fromFieldName(String value) {
        return Arrays.stream(values())
                .filter(field -> field.fieldName.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid sort field: " + value));
    }
}
