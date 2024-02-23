package com.example.todolist.document.application.dto.request;

import com.example.todolist.member.domain.entity.Member;
import com.example.todolist.document.domain.entity.Period;
import com.example.todolist.document.domain.entity.Document;
import com.example.todolist.document.domain.status.DayStatus;
import jakarta.validation.constraints.NotNull;

public record DocumentRequest(
        @NotNull
        Period period,
        @NotNull
        String title,
        @NotNull
        String description,
        String userId,
        DayStatus dayStatus
) {
    public Document toEntity(Member member) {
        return new Document(period, title, description, member, dayStatus);
    }

}
