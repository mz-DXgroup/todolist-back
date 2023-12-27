package com.example.todolist.document.web;

import com.example.todolist.document.application.TodoService;
import com.example.todolist.document.application.dto.TodoRequest;
import com.example.todolist.document.application.dto.TodoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

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

    @GetMapping("/todo")
    public  ResponseEntity<List<TodoResponse>> getTodos(){

        return ResponseEntity.ok(todoService.getTodos());
    }
}
