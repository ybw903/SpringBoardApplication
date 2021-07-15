package com.myboard.service;

import com.myboard.dto.FileUploadDto;
import com.myboard.security.PrincipalDetails;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public FileUploadDto fileUpload(MultipartFile upload, PrincipalDetails principalDetails);
}
