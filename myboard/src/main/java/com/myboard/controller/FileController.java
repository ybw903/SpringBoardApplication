package com.myboard.controller;

import com.myboard.dto.FileUploadDto;
import com.myboard.security.PrincipalDetails;
import com.myboard.service.FileService;
import com.myboard.service.FileServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/image/upload")
    public FileUploadDto imageUpload(@RequestPart MultipartFile upload, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("hi");
        return fileService.fileUpload(upload, principalDetails);
    }
}
