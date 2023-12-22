package com.example.todolist.document.application;

import com.example.todolist.document.domain.entity.Todo;
import com.example.todolist.document.domain.repository.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class TodoService {

    private  final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) { this.todoRepository = todoRepository; }

    public Integer createTodo(TodoRequest todoRequest){
        Todo todo = todoRequest.toEntity();
        todoRepository.save(todo);
        return todo.getId();
    }
}
