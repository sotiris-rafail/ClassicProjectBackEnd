package com.classic.project.model.constantParty.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileExistsException extends  RuntimeException {
    public FileExistsException(String message) {
        super(message);
    }
}