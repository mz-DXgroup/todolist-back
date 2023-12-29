package com.example.todolist.document.application;

import com.example.todolist.document.application.dto.TodoDetailResponse;
import com.example.todolist.document.application.dto.TodoRequest;
import com.example.todolist.document.application.dto.TodoResponse;
import com.example.todolist.document.application.dto.TodoUpdateRequest;
import com.example.todolist.document.domain.entity.Todo;
import com.example.todolist.document.domain.repository.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<TodoResponse> getTodos(Pageable pageable) {
        return todoRepository.findAll(pageable).map(TodoResponse::from);
    }

    public TodoDetailResponse getTodo(Integer id) {
        return TodoDetailResponse.from(todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(id + " 를 찾을 수 없습니다. ")));
    }

    public void updateTodo(Integer id, TodoUpdateRequest request) {
        var dto = request.toDto();
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(id + " 찾을 수 없습니다"));
        todo.update(dto);
    }
}
