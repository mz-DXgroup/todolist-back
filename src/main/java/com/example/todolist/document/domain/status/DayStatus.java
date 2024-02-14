package com.example.todolist.document.domain.status;

import com.example.todolist.common.domain.CodeEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DayStatus implements CodeEnum {
    VERYGOOD("VERYGOOD", "아주 만족"),
    GOOD("GOOD", "만족"),
    JUST("JUST", "그저 그럼"),
    BAD("BAD", "나쁨"),
    WORST("WORST", "최악"),
    NONE("NONE", "평가 전");

    @JsonValue
    private final String code;
    private final String description;

    DayStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator
    public static DayStatus valueOfCode(String code) {
        return CodeEnum.of(DayStatus.class, code)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코드입니다. - " + code));
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
