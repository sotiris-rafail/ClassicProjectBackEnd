package com.classic.project.model.item.unSold.response;

import com.classic.project.model.item.Grade;
import com.classic.project.model.item.ItemType;
import com.classic.project.model.item.StateOfItem;
import com.classic.project.model.item.unSold.UnSoldItem;

public class NewUnSoldItem {
    private int itemdId;
    private int startingPrice;
    private int maxPrice;
    private int currentValue;
    private int bidStep;
    private String photoPath;
    private String lastBidder;
    private String grade;
    private String typeOfItem;
    private String name;
    private String stateOfItem;

    public int getItemdId() {
        return itemdId;
    }

    public int getStartingPrice() {
        return startingPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public int getBidStep() {
        return bidStep;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String getLastBidder() {
        return lastBidder;
    }

    public String getGrade() {
        return grade;
    }

    public String getTypeOfItem() {
        return typeOfItem;
    }

    public String getName() {
        return name;
    }

    public String getStateOfItem() {
        return stateOfItem;
    }

    public static UnSoldItem convertToUnSoldItem(NewUnSoldItem newUnSoldItem){
        return new UnSoldItem(Grade.valueOf(newUnSoldItem.getGrade()), ItemType.valueOf(newUnSoldItem.getTypeOfItem()), newUnSoldItem.photoPath, newUnSoldItem.name, StateOfItem.getValueByState(newUnSoldItem.getStateOfItem()), newUnSoldItem.maxPrice, newUnSoldItem.startingPrice, newUnSoldItem.bidStep, newUnSoldItem.currentValue, newUnSoldItem.lastBidder, newUnSoldItem.bidStep);
    }
}
