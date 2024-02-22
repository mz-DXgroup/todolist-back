package com.example.todolist.file.web;

import com.example.todolist.file.application.FileService;
import com.example.todolist.file.application.response.FileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RequestMapping("/api/file")
@RequiredArgsConstructor
@RestController
public class FileController {

    private final FileService fileservice;

    @PostMapping(value = "{todoId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> addFile(
            @RequestPart("multipartFile") MultipartFile file,
            @PathVariable("todoId") Integer todoId) {
        //Long memberPK = SecurityUtil.getCurrentMemberPk();
        fileservice.uploadFile(file, todoId);    //서버 내부 스토리지 저장
        //Long success = fileService.insertFileInfo(fileinfo, memberPK);	//데이터베이스에 파일 정보 저장

        return ResponseEntity.ok(todoId);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<FileResponse> fineFile(@PathVariable("todoId") Integer todoId) {
        FileResponse fileResponse = fileservice.findFile(todoId);
        return ResponseEntity.ok(fileResponse);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("fileId") Long fileId) throws IOException {
        FileResponse fileDto = fileservice.fileDownload(fileId);
        Path path = Paths.get(fileDto.filePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));

        // 파일 이름을 UTF-8에서 ISO-8859-1로 인코딩
        String encodedFilename = URLEncoder.encode(fileDto.originalFilename(), "UTF-8");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFilename)
                .body(resource);
    }

    @DeleteMapping("/download/{fileId}")
    public ResponseEntity<Void> findDelete(@PathVariable("fileId") Long fileId) {
        fileservice.deleteFile(fileId);
        return ResponseEntity.ok().build();
    }

}

