package com.example.todolist.document.application.dto;

import com.example.todolist.document.domain.dto.TodoDto;
import com.example.todolist.document.domain.entity.Period;

public record TodoUpdateRequest(
        String todo,
        String description,
        Period period,
        Boolean isActive ,
        Integer documentId
) {
    public TodoDto toDto( ){
        return new TodoDto(todo, description, period, isActive, documentId);
    }
}
