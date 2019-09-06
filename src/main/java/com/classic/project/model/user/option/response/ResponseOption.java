package com.classic.project.model.user.option.response;

import com.classic.project.model.user.option.Option;

public class ResponseOption {

    private boolean bossesNotification;
    private boolean newItemNotification;
    private boolean soldItemNotification;

    public ResponseOption(boolean bossesNotification, boolean newItemNotification, boolean soldItemNotification) {
	this.bossesNotification = bossesNotification;
	this.newItemNotification = newItemNotification;
	this.soldItemNotification = soldItemNotification;
    }

    public boolean isBossesNotification() {
        return bossesNotification;
    }

    public void setBossesNotification(boolean bossesNotification) {
        this.bossesNotification = bossesNotification;
    }

    public boolean isNewItemNotification() {
        return newItemNotification;
    }

    public void setNewItemNotification(boolean newItemNotification) {
        this.newItemNotification = newItemNotification;
    }

    public boolean isSoldItemNotification() {
        return soldItemNotification;
    }

    public void setSoldItemNotification(boolean soldItemNotification) {
        this.soldItemNotification = soldItemNotification;
    }

    public static ResponseOption convertForUser(Option options) {
	return new ResponseOption(options.isBossesOption(), options.isNewItemOption(), options.isSoldItemOption());
    }
}
