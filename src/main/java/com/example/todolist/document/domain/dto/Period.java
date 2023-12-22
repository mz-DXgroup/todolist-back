package com.example.todolist.document.domain.dto;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
public record Period (

     LocalDateTime startDate,

     LocalDateTime endDate

 ){

}
