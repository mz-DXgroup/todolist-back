package com.example.todolist.document.application.dto;

import com.example.todolist.document.domain.entity.Period;
import com.example.todolist.document.domain.entity.Document;
import com.example.todolist.document.domain.entity.Todo;
public record TodoRequest(
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