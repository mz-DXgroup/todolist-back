package com.example.todolist.file.application.response;

import com.example.todolist.file.domain.entity.FileStore;
import lombok.Builder;

@Builder
public record FileResponse(
        Long id,
        String contentType,
        String originalFilename,
        String filePath
) {

    public static FileResponse from(FileStore fileStore) {
        return new FileResponse(fileStore.getId(),
                fileStore.getContentType(),
                fileStore.getOrigFilename(),
                fileStore.getFilePath()
        );
    }
}