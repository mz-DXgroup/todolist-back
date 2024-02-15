package com.example.todolist.document.application.dto.response;

import com.example.todolist.document.domain.entity.Period;
import com.example.todolist.document.domain.entity.Todo;

public record TodoDetailResponse(
        Integer todoId,
        Integer DocumentId,
        String todo,
        String description,
        Period period,
        boolean isActive
) {
    public static TodoDetailResponse from(Todo entity){
       return new TodoDetailResponse(
               entity.getId(),
                entity.getDocument().getId(),
                entity.getTodo(),
                entity.getDescription(),
                entity.getPeriod(),
               entity.isChecked()
        );
    }

}
