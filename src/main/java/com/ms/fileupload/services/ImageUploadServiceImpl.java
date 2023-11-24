package com.ms.fileupload.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {
    @Value("${image.basePath}")
    private String imageBasePath;
    @Override
    public boolean addAndReplace(String channel, String domain, String feature, String page, MultipartFile imageFile) throws IOException {
        String fileName = imageFile.getOriginalFilename();
        String extensionName = fileName.substring(fileName.lastIndexOf("."));
        // image upload path
        String imagePath = imageBasePath + File.separator + channel + File.separator + domain + File.separator + feature;
        checkAndCreatePath(imagePath);
        String imageWriteFile = imagePath + File.separator + page + extensionName.toLowerCase();
        File imageUploadFile = new File(imageWriteFile);
        imageFile.transferTo(imageUploadFile);
        return true;
    }

    public void checkAndCreatePath(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
}
