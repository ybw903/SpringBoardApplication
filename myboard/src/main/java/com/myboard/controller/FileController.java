package com.myboard.controller;

import com.myboard.dto.FileUploadDto;
import com.myboard.security.PrincipalDetails;
import com.myboard.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/image/upload")
    public FileUploadDto imageUpload(@RequestPart MultipartFile upload,
                                     @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return fileService.fileUpload(upload, principalDetails);
    }

    @GetMapping("/display")
    public ResponseEntity<Resource> imageDownload(@RequestParam("file_name") String fileName ) {
        log.info("hi");
        String path = "C:\\Users\\bw\\image\\";

        Resource resource = new FileSystemResource(path + fileName);

        if(!resource.exists())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        log.info("hi2");
        HttpHeaders headers = new HttpHeaders();
        Path filePath = null;

        try {
            filePath = Paths.get(path + fileName);
            headers.add("Content-Type", Files.probeContentType(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
