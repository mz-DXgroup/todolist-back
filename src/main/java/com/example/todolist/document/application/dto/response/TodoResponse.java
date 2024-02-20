package com.example.todolist.document.application.dto.response;

import com.example.todolist.document.domain.entity.Period;
import com.example.todolist.document.domain.entity.Todo;
import com.example.todolist.file.domain.entity.FileStore;

import java.util.List;
import java.util.stream.Collectors;

public record TodoResponse(
        Integer todoId,
        Integer documentId,
        String todo,
        String description,
        Period period,
        boolean isChecked,
        List<String> fileName
) {
    public static TodoResponse from(Todo entity) {
        return new TodoResponse(
                entity.getId(),
                entity.getDocument().getId(),
                entity.getTodo(),
                entity.getDescription(),
                entity.getPeriod(),
                entity.isChecked(),
                entity.getFileStores().stream()
                        .map(FileStore::getOrigFilename)
                        .toList()
        );
    }
}
