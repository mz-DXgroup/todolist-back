package com.example.todolist.document.application.dto;

import com.example.todolist.common.member.domain.entity.Member;
import com.example.todolist.document.domain.dto.Period;
import com.example.todolist.document.domain.entity.Document;

public record DocumentRequest(

        Period period,
        String title,
        String description,
        Integer memberId


) {
    public Document toEntity( ){
        return  new Document(period,title,description, Member.fromId(memberId));
    }

}
