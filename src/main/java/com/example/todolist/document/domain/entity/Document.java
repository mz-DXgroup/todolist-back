package com.example.todolist.document.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Document {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Period period;


    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Todo> todos= new ArrayList<>();


}
