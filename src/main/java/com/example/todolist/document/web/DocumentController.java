package com.example.todolist.document.web;

import com.example.todolist.document.application.TodoRequest;
import com.example.todolist.document.application.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("/api/document")
@RestController
public class DocumentController {

    private  final  TodoService todoService ;

    public DocumentController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/todo")
    public ResponseEntity<Void> createTodo (TodoRequest todoRequest){
        Integer id = todoService.createTodo(todoRequest);
        return ResponseEntity.created(URI.create("/document/todo/"+ id )).build();
    }
}
