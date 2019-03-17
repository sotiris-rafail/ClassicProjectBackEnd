package com.classic.project.model.item.unSold.response;

import com.classic.project.model.item.Grade;
import com.classic.project.model.item.ItemType;
import com.classic.project.model.item.StateOfItem;
import com.classic.project.model.item.unSold.UnSoldItem;

public class NewUnSoldItem {
    private int itemdId;
    private double startingPrice;
    private double maxPrice;
    private double currentValue;
    private double bidStep;
    private String photoPath;
    private String lastBidder;
    private String grade;
    private String typeOfItem;
    private String name;
    private String stateOfItem;
    private int numberOfDays;

    public int getItemdId() {
        return itemdId;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public double getBidStep() {
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

    public int getNumberOfDays() {
	return numberOfDays;
    }

    public static UnSoldItem convertToUnSoldItem(NewUnSoldItem newUnSoldItem){
        return new UnSoldItem(Grade.valueOf(newUnSoldItem.getGrade()), ItemType.valueOf(newUnSoldItem.getTypeOfItem()), newUnSoldItem.photoPath, newUnSoldItem.name, StateOfItem.getValueByState(newUnSoldItem.getStateOfItem()), newUnSoldItem.maxPrice, newUnSoldItem.startingPrice, newUnSoldItem.numberOfDays, newUnSoldItem.currentValue, newUnSoldItem.lastBidder, newUnSoldItem.bidStep);
    }
}
