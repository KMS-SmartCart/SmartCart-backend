package com.kms.smartcart_backend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AwsS3Service {
    String uploadImage(MultipartFile file) throws IOException;
    void deleteImage(String fileUrl);
}
