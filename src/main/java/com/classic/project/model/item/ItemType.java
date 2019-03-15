package com.classic.project.model.item;

public enum ItemType {

    ARMOR("Armor"),
    WEAPON("Weapon"),
    JEWELL("Jewell");

    private String type;
    ItemType(String jewell) {
        this.type = jewell;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
