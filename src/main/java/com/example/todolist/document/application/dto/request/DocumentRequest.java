package com.example.todolist.document.application.dto.request;

import com.example.todolist.common.member.domain.entity.Member;
import com.example.todolist.document.domain.entity.Period;
import com.example.todolist.document.domain.entity.Document;
import com.example.todolist.document.domain.status.DayStatus;

public record DocumentRequest(

        Period period,
        String title,
        String description,
        Integer memberId,
        DayStatus dayStatus


) {
    public Document toEntity( ){
        return  new Document(period,title,description, Member.fromId(memberId),dayStatus);
    }

}
