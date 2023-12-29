package com.example.todolist.document.domain.status;

import com.example.todolist.common.domain.CodeEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DayStatus implements CodeEnum {

    VERY_GOOD("VERY GOOD", "아주 만족"),
    GOOD("GOOD", "만족"),
    JUST("JUST", "그저 그럼"),
    BAD("BAD", "나쁨"),
    WORST("WORST", "최악");

    @JsonValue
    private final String code;
    private final String description;

    DayStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
    @Override
    public String getCode() {
        return code;
    }
    @Override
    public String getDescription() {
        return description;
    }

}
