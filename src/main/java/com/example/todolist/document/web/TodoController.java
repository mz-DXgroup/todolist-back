package com.example.todolist.document.web;

import com.example.todolist.document.application.dto.TodoRequest;
import com.example.todolist.document.application.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("/api")
@RestController
public class TodoController {

    private  final  TodoService todoService ;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/todo")
    public ResponseEntity<Void> createTodo (TodoRequest todoRequest){
        Integer id = todoService.createTodo(todoRequest);
        return ResponseEntity.created(URI.create("api/todo"+ id )).build();
    }
}
