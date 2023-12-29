package com.example.todolist.document.domain.status.convertor;

import com.example.todolist.common.domain.entity.CodeEnumConvertor;
import com.example.todolist.document.domain.status.DayStatus;

public class DayStatusConvertor extends CodeEnumConvertor<DayStatus> {
    @Override
    public Class<DayStatus> supprotClass() {
        return DayStatus.class ;
    }

    @Override
    public DayStatus defaultType() {
        return DayStatus.NONE;
    }
}
