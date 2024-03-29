package com.example.todolist.file.application;

import com.example.todolist.document.domain.entity.Todo;
import com.example.todolist.document.domain.port.repository.TodoRepository;
import com.example.todolist.file.application.response.FileResponse;
import com.example.todolist.file.domain.entity.FileStore;
import com.example.todolist.file.domain.port.repository.FileStoreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class FileService {

    private final FileStoreRepository fileStoreRepository;
    private final TodoRepository todoRepository;

    @Transactional
    public void uploadFiles(List<MultipartFile> files, Integer todoId) {

        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            String newFileName = generateUniqueFileName(originalFileName);

            // 저장할 파일 경로
            String uploadDirectory = "C:\\otherGitClone";
            Path filePath = Paths.get(uploadDirectory, newFileName);

            // 파일 정보 저장
            Todo todo = todoRepository.findById(todoId)
                    .orElseThrow(() -> new EntityNotFoundException("Todo not found with id: " + todoId));
            FileStore fileStore = new FileStore(newFileName, originalFileName, file.getContentType(), filePath.toString(), todo);
            fileStoreRepository.save(fileStore);

            // 서버 내부 스토리지에 업로드
            try {
                Files.copy(file.getInputStream(), filePath);
            } catch (IOException e) {
                throw new IllegalArgumentException("File upload exception. " + e.getStackTrace());
            }
        }
    }

    public FileResponse findFile(Integer todoId) {

        return fileStoreRepository.findAll()
                .stream()
                .filter(i -> i.getTodo().getId().equals(todoId))
                .map(FileResponse::from).findFirst().orElseThrow(() -> new IllegalArgumentException("파일이 존재하지 않습니다."));   //todo 파일 여러개 일 경우 변경 필요
    }

    @Transactional
    public FileResponse fileDownload(Long id) {
        FileStore file = fileStoreRepository.findById(id).get();

        FileResponse fileDto = FileResponse.builder()
                .id(id)
                .originalFilename(file.getOrigFilename())
                .filePath(file.getFilePath())
                .contentType(file.getContentType())
                .build();

        return fileDto;
    }


    private String generateUniqueFileName(String originalFileName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        // Random 객체 생성
        Random random = new Random();
        // 0 이상 100 미만의 랜덤한 정수 반환
        String randomNumber = Integer.toString(random.nextInt(Integer.MAX_VALUE));
        String timeStamp = dateFormat.format(new Date());
        return timeStamp + randomNumber + originalFileName;
    }

    public void deleteFile(Long fileId) {
        fileStoreRepository.deleteById(fileId);
    }
}
