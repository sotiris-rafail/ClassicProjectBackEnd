package com.classic.project.model.user.exception;

import com.classic.project.model.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserExistException extends RuntimeException {

    public UserExistException(User user) {
        super(getErrorMessage(user));
    }

    private static String getErrorMessage(User user) {
        if(user.getPassword() == null) {
            return "Password cannot be null value";
        } else {
             return "User " + user.getEmail() + " exist";
        }
    }
}
