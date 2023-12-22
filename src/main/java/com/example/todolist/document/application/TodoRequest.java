package com.example.todolist.document.application;

import com.example.todolist.document.domain.dto.Period;
import com.example.todolist.document.domain.entity.Todo;

public record TodoRequest(

        String todo,
        Period period,
        boolean isActive
) {
    public Todo toEntity( ){
        return  new Todo(todo,period,isActive);
    }

}
