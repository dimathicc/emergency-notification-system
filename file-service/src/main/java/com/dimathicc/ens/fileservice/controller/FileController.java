package com.dimathicc.ens.fileservice.controller;


import com.dimathicc.ens.fileservice.dto.UserRequest;
import com.dimathicc.ens.fileservice.dto.UserResponse;
import com.dimathicc.ens.fileservice.service.FileReaderService;
import com.dimathicc.ens.fileservice.service.UserClient;
import jakarta.validation.Valid;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileReaderService readerService;

    public FileController(FileReaderService readerService) {
        this.readerService = readerService;
    }

    @PostMapping
    public ResponseEntity<Boolean> createUsersFromFile(@RequestPart MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(readerService.createUsersFromXlsx(file));
    }

    @GetMapping
    public ResponseEntity<ByteArrayResource> downloadFile() {

        byte[] users = readerService.downloadFileWithUsers();
        ByteArrayResource resource = new ByteArrayResource(users);

        return ResponseEntity.ok()
                .contentLength(users.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"users.xlsx\"")
                .body(resource);
    }
}
