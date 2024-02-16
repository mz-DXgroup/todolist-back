package com.example.todolist.document.domain.entity;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;


@Embeddable
public record Period(
        LocalDate startDate,
        LocalDate endDate
) {
    private static void validate(LocalDate startDate, LocalDate endDate) {

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작 시간과 끝나는 시간을 확인해 주세요");
        }
    }
}
