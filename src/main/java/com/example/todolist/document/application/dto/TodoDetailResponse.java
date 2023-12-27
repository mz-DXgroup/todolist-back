package com.example.todolist.document.application.dto;

import com.example.todolist.document.domain.entity.Period;
import com.example.todolist.document.domain.entity.Todo;

public record TodoDetailResponse(
        String todo,
        String description,
        Period period,
        boolean isActive
) {
    public static TodoDetailResponse from(Todo entity){
       return new TodoDetailResponse(
                entity.getTodo(),
                entity.getDescription(),
                entity.getPeriod(),
               entity.isActive()
        );
    }

}
