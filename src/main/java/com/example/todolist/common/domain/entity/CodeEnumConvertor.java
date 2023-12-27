package com.example.todolist.common.domain.entity;

import com.example.todolist.common.domain.CodeEnum;
import jakarta.persistence.AttributeConverter;

public abstract class CodeEnumConvertor<T extends Enum<T> & CodeEnum> implements AttributeConverter<T, String> {

    @Override
    public String convertToDatabaseColumn(T attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        return CodeEnum.of(supprotClass(), dbData).orElseGet(this::defaultType);
    }

    public abstract Class<T> supprotClass();

    public abstract T defaultType();
}
