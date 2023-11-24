package com.ms.fileupload.model;

import org.springframework.stereotype.Repository;

@Repository
public class ResponseMessage {
    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
