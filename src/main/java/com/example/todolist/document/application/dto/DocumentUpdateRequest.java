package com.example.todolist.document.application.dto;

import com.example.todolist.document.domain.dto.Period;

public record DocumentUpdateRequest(

        Period period,
        String title,
        String description

) {
}
