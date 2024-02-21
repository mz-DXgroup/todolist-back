package com.example.todolist.document.application.dto.response;

import com.example.todolist.document.domain.entity.Period;
import com.example.todolist.document.domain.entity.Todo;

public record TodoTodayResponse(
        Integer todoId,
        Integer documentId,
        String todo,
        String description,
        Period period,
        boolean isChecked
) {
    public static TodoTodayResponse from(Todo todo) {
        return new TodoTodayResponse(
                todo.getId(),
                todo.getDocument().getId(),
                todo.getTodo(),
                todo.getDescription(),
                todo.getPeriod(),
                todo.isChecked());
    }
}
