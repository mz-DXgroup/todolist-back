package com.example.todolist.document.application.dto.response;

import com.example.todolist.document.domain.entity.Period;
import com.example.todolist.document.domain.entity.Todo;

import java.util.List;

public record TodoResponse(
        Integer todoId,
        Integer documentId,
        String todo,
        String description,
        Period period,
        boolean isChecked,
        List<FileResponse> fileResponses
) {
    public static TodoResponse from(Todo entity) {
        return new TodoResponse(
                entity.getId(),
                entity.getDocument().getId(),
                entity.getTodo(),
                entity.getDescription(),
                entity.getPeriod(),
                entity.isChecked(),
                entity.getFileStores().stream().map(fileStore -> FileResponse.from(fileStore.getOrigFilename(), fileStore.getId())).toList()

        );
    }

    public record FileResponse(
            String fileName,
            Long fileId
    ) {

        public static FileResponse from(String fileName, Long fileId) {
            return new FileResponse(fileName, fileId);

        }
    }
}
