package com.dimathicc.ens.userservice.controller;

import com.dimathicc.ens.userservice.service.FileReaderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    Logger log = LoggerFactory.getLogger(FileController.class);

    private final FileReaderService readerService;

    public FileController(FileReaderService readerService) {
        this.readerService = readerService;
    }

    @PostMapping
    public ResponseEntity<Boolean> createUsersFromFile(@RequestPart MultipartFile file) {
        log.info("MultipartFile " + file);
        return ResponseEntity.status(HttpStatus.CREATED).body(readerService.readUsersFromXlsx(file));
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
