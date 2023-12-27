package com.example.todolist.document.domain.dto;

import com.example.todolist.document.domain.entity.Period;

public record TodoDto(
        String todo,
        String description,
        Period period,
        boolean isActive,
        Integer documentId
) {
}
