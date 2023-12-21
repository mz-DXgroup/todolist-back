package com.example.todolist.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer id;

    private String name;

    private String email;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Board> todos= new ArrayList<>();
}
