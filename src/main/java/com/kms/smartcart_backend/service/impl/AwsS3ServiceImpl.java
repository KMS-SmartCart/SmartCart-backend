package com.kms.smartcart_backend.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.kms.smartcart_backend.response.exception.Exception400;
import com.kms.smartcart_backend.response.exception.Exception500;
import com.kms.smartcart_backend.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3ServiceImpl implements AwsS3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;


    @Transactional
    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        if(file == null) throw new Exception400.ProductBadRequest("imageFile = null");

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        String fileName = file.getOriginalFilename();
        amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);
        String fileUrl = "https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + fileName;
        log.info("Success File Upload - newFileName: {}, newFileUrl: {}", fileName, fileUrl);

        return fileUrl;
    }

    @Transactional
    @Override
    public void deleteImage(String fileUrl) {
        if(fileUrl == null) throw new Exception500.AwsS3Server("imageFileUrl = null");
        String fileName = fileUrl.split(".com/")[1];

        try {
            amazonS3Client.deleteObject(bucketName, fileName);
            log.info("Success File Delete - deleteFileName: {}, deleteFileUrl: {}", fileName, fileUrl);
        } catch (AmazonServiceException e) {
            log.info("Error File Delete - errorFileName: {}, errorFileUrl: {}", fileName, fileUrl);
            throw new Exception500.AwsS3Server("AWS S3 서버 이미지 처리 에러");
        }
    }
}
