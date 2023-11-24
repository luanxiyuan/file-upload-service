package com.ms.fileupload.controller;

import com.ms.fileupload.constant.Const;
import com.ms.fileupload.model.ResponseMessage;
import com.ms.fileupload.services.ImageUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/v1/uploader")
public class ImageUploadController {

    @Value("${image.maxSize}")
    public String imageMaxSize;

    private static final Logger log = LoggerFactory.getLogger(ImageUploadController.class);

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    public ResponseMessage responseMessage;


    @PostMapping("/image")
    @CrossOrigin(origins = "*")
    public ResponseEntity<ResponseMessage> addAndReplace(
            @RequestParam("image") MultipartFile imageFle,
            @RequestParam("channel") String channel,
            @RequestParam("domain") String domain,
            @RequestParam("feature") String feature,
            @RequestParam("page") String page) {
        if(imageFle.isEmpty()) {
            responseMessage.setMessage(Const.MSG_IMAGE_IS_EMPTY);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
        if (!verifyImage(imageFle)) {
            responseMessage.setMessage(Const.MSG_INVALID_IMAGE_TYPE);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
        if(!verifyImageSize(imageFle)) {
            responseMessage.setMessage(Const.MSG_INVALID_IMAGE_SIZE);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
        if(channel.isEmpty() || domain.isEmpty() || feature.isEmpty() || page.isEmpty()) {
            responseMessage.setMessage(Const.MSG_INVALID_PARAMETERS);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
        try {
            imageUploadService.addAndReplace(channel, domain, feature, page, imageFle);
            log.warn("Upload image:" + channel + File.separator + domain + File.separator + feature + File.separator + page);
            responseMessage.setMessage(Const.MSG_IMAGE_UPLOAD_SUCCESSFUL);
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (IOException e) {
            log.error(e.toString());
            responseMessage.setMessage(Const.MSG_IMAGE_UPLOAD_FAILED);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @GetMapping("/image")
    @ResponseBody
    public ResponseEntity<ResponseMessage> getImage() {
        log.info("Upload image - debug log");
        log.warn("Upload image - warn log");
        log.error("Upload image - error log");
        responseMessage.setMessage(Const.MSG_IMAGE_IS_EMPTY);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
    }

    public boolean verifyImage(MultipartFile imageFile) {
        String contentType = imageFile.getContentType();
        return !("image/jpeg").equals(contentType) && !("image/png").equals(contentType);
    }

    public boolean verifyImageSize(MultipartFile imageFile) {
        long maxFileSize = Long.parseLong(imageMaxSize);
        return imageFile.getSize() <= maxFileSize;
    }
}
