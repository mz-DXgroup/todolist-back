package com.example.todolist.document.domain.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


@Embeddable
public record Period(
        @NotNull(message = "startDate는 null일 수 없습니다.")
        LocalDate startDate,
        @NotNull(message = "endDate는 null일 수 없습니다.")
        LocalDate endDate
) {
    public Period {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작  시간과 끝나는 시간을 확인해주세요");
        }
    }
}
