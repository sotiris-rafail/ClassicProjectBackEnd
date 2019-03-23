package com.classic.project.model.character.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CharacterNotFoundException extends RuntimeException {

    public CharacterNotFoundException(int charId) {
	super("Character with id " + charId + " does not exist.");
    }
}