package com.example.todolist.document.application.dto;

import com.example.todolist.document.domain.entity.Period;
import com.example.todolist.document.domain.entity.Document;

public record DocumentResponse(

        Period period,
        String title,
        String description
) {
    public static DocumentResponse from(Document entity){
       return new DocumentResponse(
                entity.getPeriod(),
                entity.getTitle(),
                entity.getDescription()
        );
    }

}
