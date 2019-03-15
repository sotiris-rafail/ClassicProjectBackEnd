package com.classic.project.model.item.sold.response;

import com.classic.project.model.item.sold.SoldItem;

public class ResponseSoldItem {

    private int itemId;
    private String grade;
    private String typeOfItem;
    private int price;
    private String stateOfItem;
    private String whoBoughtIt;
    private String name;
    private boolean delivered;
    private String photoPath;

    public ResponseSoldItem(int itemId, String grade, String typeOfItem, int price, String stateOfItem, String whoBoughtIt, String name, boolean delivered, String photoPath) {
        this.itemId = itemId;
        this.grade = grade;
        this.typeOfItem = typeOfItem;
        this.price = price;
        this.stateOfItem = stateOfItem;
        this.whoBoughtIt = whoBoughtIt;
        this.name = name;
        this.delivered = delivered;
        this.photoPath = photoPath;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTypeOfItem() {
        return typeOfItem;
    }

    public void setTypeOfItem(String typeOfItem) {
        this.typeOfItem = typeOfItem;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStateOfItem() {
        return stateOfItem;
    }

    public void setStateOfItem(String stateOfItem) {
        this.stateOfItem = stateOfItem;
    }

    public String getWhoBoughtIt() {
        return whoBoughtIt;
    }

    public void setWhoBoughtIt(String whoBoughtIt) {
        this.whoBoughtIt = whoBoughtIt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public static ResponseSoldItem convertForDisplay(SoldItem soldItem){
        return new ResponseSoldItem(soldItem.getItemId(), soldItem.getGrade().getGrade(), soldItem.getItemType().getType(), soldItem.getMaxPrice(), soldItem.getStateOfItem().getState(), soldItem.getWhoBoughtIt(), soldItem.getItemName(), soldItem.isDelivered(), soldItem.getPhotoPath());
    }
}
