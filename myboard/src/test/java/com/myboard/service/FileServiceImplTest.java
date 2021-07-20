package com.myboard.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;

class FileServiceImplTest {

    @Test
    @DisplayName("업로드 테스트")
    void fileUploadTest() throws NoSuchMethodException {
        FileServiceImpl fileService = new FileServiceImpl();

        Method method = fileService.getClass().getDeclaredMethod("upload", MultipartFile.class);
        method.setAccessible(true);


    }
}