package com.example.todolist.document.application;

import com.example.todolist.document.application.dto.TodoDetailResponse;
import com.example.todolist.document.application.dto.TodoRequest;
import com.example.todolist.document.application.dto.TodoResponse;
import com.example.todolist.document.application.dto.TodoUpdateRequest;
import com.example.todolist.document.domain.entity.Todo;
import com.example.todolist.document.domain.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Integer createTodo(TodoRequest todoRequest) {
        Todo todo = todoRequest.toEntity();
        todoRepository.save(todo);
        return todo.getId();
    }

    public List<TodoResponse> getTodos() {
        return todoRepository.findAll().stream().map(TodoResponse::from).toList();
    }

    public TodoDetailResponse getTodo(Integer id) {
        return TodoDetailResponse.from(todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(id + " 를 찾을 수 업습니다. ")));
    }

    public void updateTodo(Integer id, TodoUpdateRequest request) {
        val dto = request.toDto();
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(id + " 찾을 수 없습니다"));
        todo.update(dto);
    }
}
