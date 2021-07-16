package com.myboard.service;

import com.myboard.dto.FileUploadDto;
import com.myboard.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

    private String file_path = "C:\\Users\\bw\\image";

    @Override
    public FileUploadDto fileUpload(MultipartFile upload, PrincipalDetails principalDetails) {
        return upload(upload);
    }

    private FileUploadDto upload(MultipartFile uploadFile) {
        UUID uid = UUID.randomUUID();
        OutputStream out = null;
        PrintWriter printWriter = null;

        try {
            String fileName =  uid + "_" + uploadFile.getOriginalFilename();
            byte[] bytes = uploadFile.getBytes();

            String uploadPath = file_path + File.separator + fileName;
            File file = new File(uploadPath);
            out = new FileOutputStream(file);
            out.write(bytes);
            out.flush();

            String url = "/display?file_name="+fileName;
            return FileUploadDto.builder()
                        .uploaded(true)
                        .fileName(uid+"_"+fileName)
                        .url(url)
                        .build();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(out!=null)
                    out.close();
                if(printWriter != null)
                    printWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return FileUploadDto.builder()
                        .uploaded(false)
                        .build();
    }


}
