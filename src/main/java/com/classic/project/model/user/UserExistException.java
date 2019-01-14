package com.classic.project.model.user;

public class UserExistException extends RuntimeException{
    public UserExistException(String email){
        super("User " +email +" exist");
    }
}
