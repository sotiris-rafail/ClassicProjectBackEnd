package com.classic.project.model.clan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClanExistException extends RuntimeException {

    public ClanExistException(String name) {
        super("Clan with name " + name + "already exists");
    }
}
