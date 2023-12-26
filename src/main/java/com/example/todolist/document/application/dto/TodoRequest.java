package com.example.todolist.document.application.dto;

import com.example.todolist.document.domain.dto.Period;
import com.example.todolist.document.domain.entity.Document;
import com.example.todolist.document.domain.entity.Todo;
public record TodoRequest(
        String todo,
        String description,
        Period period,
        Boolean isActive ,  // 기본값 추가
        Integer documentId
) {
    public Todo toEntity( ){
        return new Todo(todo, description, period, isActive, Document.fromId(documentId));
    }
}
