package com.classic.project.model.character.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CharacterExistException extends RuntimeException {

    public CharacterExistException(String inGameName) {
        super("Character with name "+ inGameName +" already exists");
    }
}
