package com.example.todolist.document.domain.entity;

import com.example.todolist.document.domain.dto.Period;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@Getter
@Entity
public class Todo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String todo;

    private Period period;

    private boolean isActive;

    public Todo( String todo, Period period, boolean isActive) {
        this.todo = todo;
        this.period = period;
        this.isActive = isActive;
    }
}
