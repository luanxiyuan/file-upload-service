package com.ms.fileupload.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageUploadService {

    boolean addAndReplace(String channel, String domain, String feature, String page, MultipartFile imageFile) throws IOException;

}
