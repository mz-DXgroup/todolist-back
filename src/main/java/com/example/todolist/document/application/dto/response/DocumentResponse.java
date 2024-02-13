package com.example.todolist.document.application.dto.response;

import com.example.todolist.document.domain.entity.Period;
import com.example.todolist.document.domain.entity.Document;

public record DocumentResponse(
        Integer documentId,
        Period period,
        String title,
        String description
) {
    public static DocumentResponse from(Document entity) {
        return new DocumentResponse(
                entity.getId(),
                entity.getPeriod(),
                entity.getTitle(),
                entity.getDescription()
        );
    }

}
