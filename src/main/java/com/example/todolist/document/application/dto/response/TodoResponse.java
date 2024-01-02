package com.example.todolist.document.application.dto.response;

import com.example.todolist.document.domain.entity.Period;
import com.example.todolist.document.domain.entity.Todo;

public record TodoResponse(
        String todo,
        Period period,
        boolean isChecked
) {
    public static TodoResponse from(Todo entity){
       return new TodoResponse(
                entity.getTodo(),
                entity.getPeriod(),
               entity.isChecked()
        );
    }

}
