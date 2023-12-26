package com.example.todolist.document.application.dto;

import com.example.todolist.document.domain.dto.Period;
import com.example.todolist.document.domain.entity.Document;

public record DocumentResponse(

        Period period,
        String title,
        String description,
        Integer memberId


) {
    public static DocumentResponse from(Document entity){
       return new DocumentResponse(
                entity.getPeriod(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getMember().getId()
        );
    }

}
