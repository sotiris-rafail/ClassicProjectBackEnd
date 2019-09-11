package com.classic.project.model.user.verification;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VerificationEmailException extends RuntimeException {

    public VerificationEmailException() {
        super();
    }

    public VerificationEmailException(String text) {
        super(text);
    }
}
