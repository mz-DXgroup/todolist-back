package com.example.todolist.document.application.dto;

public class FileInfoRequest {

    private String contentType;
    private String originalFilename;
    private String filePath;
    private String fileSize;

    public FileInfoRequest(String contentType, String originalFilename, String  filePath, String fileSize) {
        this.contentType = contentType;
        this.originalFilename = originalFilename;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

}
//todo 필요없으면 삭제 필요