package com.example.todolist.document.application.dto.response;

import com.example.todolist.document.domain.entity.Period;
import com.example.todolist.document.domain.entity.Document;

public record DocumentDetailResponse(
        Integer documentId,
        Period period,
        String title,
        String description,
        Integer memberId

) {
    public static DocumentDetailResponse from(Document entity) {
        return new DocumentDetailResponse(
                entity.getId(),
                entity.getPeriod(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getMember().getId()
        );
    }

}
