package com.classic.project.model.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User does not exist");
    }

    public UserNotFoundException(int userId) {
        super("User with ID: "+ userId +" does not exist");
    }

    public UserNotFoundException(String email){
        super("User : " + email + " does not exist.");
    }
}
