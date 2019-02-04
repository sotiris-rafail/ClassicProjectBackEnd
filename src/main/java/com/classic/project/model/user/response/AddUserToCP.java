package com.classic.project.model.user.response;

import java.util.Map;

public class AddUserToCP {
    private Map<String, String[]> updatableObject;

    public Map<String, String[]> getUpdatableObject() {
        return updatableObject;
    }

    public void setUpdatableObject(Map<String, String[]> updatableObject) {
        this.updatableObject = updatableObject;
    }
}
