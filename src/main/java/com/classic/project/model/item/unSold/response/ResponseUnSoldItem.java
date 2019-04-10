package com.classic.project.model.item.unSold.response;

import com.classic.project.model.item.unSold.UnSoldItem;

import java.util.Date;

public class ResponseUnSoldItem {

    private int itemId;
    private String grade;
    private String typeOfItem;
    private double maxPrice;
    private double startingPrice;
    private String stateOfItem;
    private String name;
    private Date expirationDate;
    private double bidStep;
    private double currentValue;
    private String lastBidder;
    private String photoPath;

    public ResponseUnSoldItem(int itemId, String grade, String typeOfItem, double maxPrice, double startingPrice,
	String stateOfItem, String name, Date expirationDate, double bidStep, double currentValue, String lastBidder,
	String photoPath) {
	this.itemId = itemId;
	this.grade = grade;
	this.typeOfItem = typeOfItem;
	this.maxPrice = maxPrice;
	this.startingPrice = startingPrice;
	this.stateOfItem = stateOfItem;
	this.name = name;
	this.expirationDate = expirationDate;
	this.bidStep = bidStep;
	this.currentValue = currentValue;
	this.lastBidder = lastBidder;
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

    public double getMaxPrice() {
	return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
	this.maxPrice = maxPrice;
    }

    public double getStartingPrice() {
	return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
	this.startingPrice = startingPrice;
    }

    public String getStateOfItem() {
	return stateOfItem;
    }

    public void setStateOfItem(String stateOfItem) {
	this.stateOfItem = stateOfItem;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Date getExpirationDate() {
	return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
	this.expirationDate = expirationDate;
    }

    public double getBidStep() {
	return bidStep;
    }

    public void setBidStep(double bidStep) {
	this.bidStep = bidStep;
    }

    public double getCurrentValue() {
	return currentValue;
    }

    public void setCurrentValue(double currentValue) {
	this.currentValue = currentValue;
    }

    public String getLastBidder() {
	return lastBidder;
    }

    public void setLastBidder(String lastBidder) {
	this.lastBidder = lastBidder;
    }

    public String getPhotoPath() {
	return photoPath;
    }

    public void setPhotoPath(String photoPath) {
	this.photoPath = photoPath;
    }

    public static ResponseUnSoldItem convertToResponse(UnSoldItem unSoldItem) {
	return new ResponseUnSoldItem(unSoldItem.getItemId(), unSoldItem.getGrade().getGrade(), unSoldItem.getItemType().getType(), unSoldItem.getMaxPrice(), unSoldItem.getStartingPrice(), unSoldItem.getStateOfItem().getState(), unSoldItem.getItemName(), unSoldItem.getExpirationDate(), unSoldItem.getBidStep(), unSoldItem.getCurrentValue(), unSoldItem.getLastBidder(), unSoldItem.getPhotoPath());
    }
}
