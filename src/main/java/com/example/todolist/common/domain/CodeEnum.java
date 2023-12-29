package com.example.todolist.common.domain;

import java.util.Optional;

public interface CodeEnum {

    String getCode();

    String getDescription();

    static <T extends Enum<? extends CodeEnum>> Optional<T> of(Class<T> enumClass, String code) {
        return Optional.ofNullable(valueOfCode(enumClass, code));
    }

    static <T extends Enum<? extends CodeEnum>> T valueOfCode(Class<T> enumClass, String code) {
        for (T value : enumClass.getEnumConstants()) {
            if (((CodeEnum) value).getCode().equalsIgnoreCase(code)) {
                return value;
            }
        }
        return null;
    }
}
