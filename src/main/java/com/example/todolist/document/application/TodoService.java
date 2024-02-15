package com.example.todolist.document.application;

import com.example.todolist.document.application.dto.request.TodoRequest;
import com.example.todolist.document.application.dto.request.TodoUpdateRequest;
import com.example.todolist.document.application.dto.response.TodoDetailResponse;
import com.example.todolist.document.application.dto.response.TodoResponse;
import com.example.todolist.document.domain.entity.Todo;
import com.example.todolist.document.domain.repository.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public Page<TodoResponse> getTodos(Integer documentId,Pageable pageable) {

         List<TodoResponse> todosResponse= todoRepository.findAll(pageable)
                 .stream()
                 .filter(i->i.getDocument().getId().equals(documentId))
                 .map(TodoResponse::from)
                 .toList();

        return new PageImpl<>(todosResponse,pageable,todosResponse.size());
    }

    public TodoDetailResponse getTodo(Integer id) {
        return TodoDetailResponse.from(todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(id + " 를 찾을 수 없습니다. ")));
    }

    public void updateTodo(Integer id, TodoUpdateRequest request) {
        var dto = request.toDto();
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(id + " 찾을 수 없습니다"));
        todo.update(dto);
    }

    public void deleteTodo(Integer todoId) {
        todoRepository.deleteById(todoId);
    }
}
