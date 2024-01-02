package com.example.todolist.document.domain.entity;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;


@Embeddable
public record Period(

        LocalDateTime startDate,

        LocalDateTime endDate

) {
    public Period(LocalDateTime startDate, LocalDateTime endDate) {
        validate(startDate,endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }
    private static void validate(LocalDateTime startDate, LocalDateTime endDate) {

            if (startDate.isAfter(endDate)) {
                throw new IllegalArgumentException("시작 시간과 끝나는 시간을 확인해 주세요");
            }
    }
}
