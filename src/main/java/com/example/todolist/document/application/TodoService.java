package com.example.todolist.document.application;

import com.example.todolist.common.exception.CustomException;
import com.example.todolist.common.exception.ExceptionStatus;
import com.example.todolist.document.application.dto.request.TodoRequest;
import com.example.todolist.document.application.dto.request.TodoUpdateRequest;
import com.example.todolist.document.application.dto.response.DocumentResponse;
import com.example.todolist.document.application.dto.response.TodoDetailResponse;
import com.example.todolist.document.application.dto.response.TodoResponse;
import com.example.todolist.document.application.dto.response.TodoTodayResponse;
import com.example.todolist.document.domain.dto.TodoDto;
import com.example.todolist.document.domain.entity.Document;
import com.example.todolist.document.domain.entity.Period;
import com.example.todolist.document.domain.entity.Todo;
import com.example.todolist.document.domain.port.repository.DocumentRepository;
import com.example.todolist.document.domain.port.repository.TodoRepository;
import com.example.todolist.file.domain.port.repository.FileStoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final DocumentRepository documentRepository;

    private final FileStoreRepository fileStoreRepository;

    public TodoService(TodoRepository todoRepository, DocumentRepository documentRepository, FileStoreRepository fileStoreRepository) {
        this.todoRepository = todoRepository;
        this.documentRepository = documentRepository;
        this.fileStoreRepository = fileStoreRepository;
    }

    public Integer createTodo(TodoRequest todoRequest) {

        Todo todo = todoRequest.toEntity();
        Document document = documentRepository.findById(
                todo.getDocument().getId()).orElseThrow(() ->
                new CustomException(ExceptionStatus.POST_IS_EMPTY)
        );

        if (isTodoWithinRange(todo.getPeriod(), document.getPeriod())) {
            todoRepository.save(todo);
        } else throw new CustomException(ExceptionStatus.PERIOD_NOT_MATCH);

        return todo.getId();
    }

    public Page<TodoResponse> getTodos(Integer documentId, Pageable pageable) {

        List<TodoResponse> todosResponse = todoRepository.findAll(pageable)
                .stream()
                .filter(i -> i.getDocument().getId().equals(documentId))
                .map(TodoResponse::from)
                .toList();

        return new PageImpl<>(todosResponse, pageable, todosResponse.size());
    }

    public TodoDetailResponse getTodo(Integer id) {
        return TodoDetailResponse.from(todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(id + " 를 찾을 수 없습니다. ")));
    }

    public void updateTodo(Integer id, TodoUpdateRequest request) {
        TodoDto dto = request.toDto();
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(id + " 찾을 수 없습니다"));
        todo.update(dto);
    }

    public void deleteTodo(Integer todoId) {
        todoRepository.deleteById(todoId);
    }

    public void deleteTodoAll() {
        todoRepository.deleteAll();
    }

    public List<TodoTodayResponse> getTodoToday(Integer userId) {
        return todoRepository.findAll()
                .stream()
                .filter(i -> i.getDocument().getMember().getId().equals(userId))
                .filter(i -> isTodayWithinRange(LocalDate.now(), i.getPeriod().startDate(), i.getPeriod().endDate()))
                .map(TodoTodayResponse::from).toList();
    }

    private static boolean isTodayWithinRange(LocalDate now, LocalDate startDate, LocalDate endDate) {
        return now.isEqual(startDate) || (now.isAfter(startDate) && now.isBefore(endDate));
    }

    private static boolean isTodoWithinRange(Period todoPeriod, Period documentPeriod) {
        return todoPeriod.startDate().isAfter(documentPeriod.startDate()) || todoPeriod.startDate().isEqual(documentPeriod.startDate())
                && todoPeriod.endDate().isBefore(documentPeriod.endDate()) || todoPeriod.endDate().isEqual(documentPeriod.endDate());
    }

}

