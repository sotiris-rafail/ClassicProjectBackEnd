package com.classic.project.model.item.sold.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SoldItemNotFoundException extends RuntimeException {

    public SoldItemNotFoundException(int itemId) {
        super("Item with ID " + itemId +" does not exist. Contact administration");
    }
}
