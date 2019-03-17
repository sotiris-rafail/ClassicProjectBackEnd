package com.classic.project.model.item.unSold;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnSoldItemsNotFoundException extends RuntimeException {
    public UnSoldItemsNotFoundException(String message) {
        super(message);
    }

    public UnSoldItemsNotFoundException(int itemId) {
	super("Item with ID " + itemId + " does not exist. Please contact administration");
    }
}
