package com.example.todolist.document.application.dto.request;

import com.example.todolist.document.domain.entity.Period;
import com.example.todolist.document.domain.status.DayStatus;

public record DocumentUpdateRequest(

        Period period,
        String title,
        String description,
        DayStatus dayStatus


) {
}
