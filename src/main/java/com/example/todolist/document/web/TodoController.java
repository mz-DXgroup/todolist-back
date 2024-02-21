package com.example.todolist.document.web;

import com.example.todolist.document.application.TodoService;
import com.example.todolist.document.application.dto.response.TodoDetailResponse;
import com.example.todolist.document.application.dto.request.TodoRequest;
import com.example.todolist.document.application.dto.response.TodoResponse;
import com.example.todolist.document.application.dto.request.TodoUpdateRequest;
import com.example.todolist.document.application.dto.response.TodoTodayResponse;
import com.example.todolist.document.domain.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/api")
@RestController
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/todo")
    public ResponseEntity<Void> createTodo(@RequestBody TodoRequest todoRequest) {
        Integer id = todoService.createTodo(todoRequest);
        return ResponseEntity.created(URI.create("api/todo/" + id)).build();
    }

    @GetMapping("/todos/{documentId}")
    public ResponseEntity<Page<TodoResponse>> getTodos(@PathVariable Integer documentId, @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(todoService.getTodos(documentId, pageable));
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<TodoDetailResponse> getTodo(@PathVariable Integer todoId) {
        return ResponseEntity.ok(todoService.getTodo(todoId));
    }

    @GetMapping("/todo/today/{userId}")
    public ResponseEntity<List<TodoTodayResponse>> getTodoToday(@PathVariable Integer userId) {
        return ResponseEntity.ok(todoService.getTodoToday(userId));
    }

    @PutMapping("/todo/{todoId}")
    public ResponseEntity<Void> updateTodo(@PathVariable Integer todoId, @RequestBody TodoUpdateRequest todoUpdateReqeust) {
        todoService.updateTodo(todoId, todoUpdateReqeust);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/todo")
    public ResponseEntity<Void> deleteTodo(@RequestParam(name = "todoId") Integer todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/todo/all/{documentId}")
    public ResponseEntity<Void> deleteTodoAll(@PathVariable("documentId") Integer documentId) {
        todoService.deleteTodoAll(documentId);
        return ResponseEntity.ok().build();
    }
}
