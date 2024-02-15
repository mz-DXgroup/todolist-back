package com.example.todolist.document.application.dto.response;

import com.example.todolist.document.domain.entity.Period;
import com.example.todolist.document.domain.entity.Todo;

public record TodoResponse(
        Integer todoId,
        Integer documentId,
        String todo,
        String description,
        Period period,
        boolean isChecked
) {
    public static TodoResponse from(Todo entity) {
        return new TodoResponse(
                entity.getId(),
                entity.getDocument().getId(),
                entity.getTodo(),
                entity.getDescription(),
                entity.getPeriod(),
                entity.isChecked()
        );
    }

}
