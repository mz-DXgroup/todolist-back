package com.example.todolist.document.application.dto;

import com.example.todolist.document.domain.dto.Period;
import com.example.todolist.document.domain.entity.Document;
import com.example.todolist.document.domain.entity.Todo;

public record TodoUpdateRequest(
        String todo,
        String description,
        Period period,
        Boolean isActive ,
        Integer documentId
) {
    public Todo toEntity( ){
        return new Todo(todo, description, period, isActive, Document.fromId(documentId));
    }
}
