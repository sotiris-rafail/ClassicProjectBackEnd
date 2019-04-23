package com.classic.project.model.item.sold;

import com.classic.project.model.item.Grade;
import com.classic.project.model.item.Item;
import com.classic.project.model.item.ItemType;
import com.classic.project.model.item.StateOfItem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class SoldItem extends Item {

    @Column
    private double maxPrice;
    @Column
    private double boughtPrice;
    @Column
    private String whoBoughtIt;
    @Column
    private boolean isDelivered;
    @Column
    private int unSoldItemId;

    public SoldItem(Grade grade, ItemType itemType, String photoPath, String itemName, StateOfItem stateOfItem, double maxPrice, double boughtPrice, String whoBoughtIt, boolean isDelivered, int unSoldItemId) {
        super(grade, itemType, photoPath, itemName, stateOfItem);
        this.maxPrice = maxPrice;
        this.boughtPrice = boughtPrice;
        this.whoBoughtIt = whoBoughtIt;
        this.isDelivered = isDelivered;
        this.unSoldItemId = unSoldItemId;
    }

    public SoldItem(int itemId, Grade grade, ItemType itemType, String photoPath, String itemName, StateOfItem stateOfItem, double maxPrice, double boughtPrice, String whoBoughtIt, boolean isDelivered, int unSoldItemId) {
        super(itemId, grade, itemType, photoPath, itemName, stateOfItem);
        this.maxPrice = maxPrice;
        this.boughtPrice = boughtPrice;
        this.whoBoughtIt = whoBoughtIt;
        this.isDelivered = isDelivered;
        this.unSoldItemId = unSoldItemId;
    }

    public SoldItem(){
        super();
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getBoughtPrice() {
        return boughtPrice;
    }

    public void setBoughtPrice(int boughtPrice) {
        this.boughtPrice = boughtPrice;
    }

    public String getWhoBoughtIt() {
        return whoBoughtIt;
    }

    public void setWhoBoughtIt(String whoBoughtIt) {
        this.whoBoughtIt = whoBoughtIt;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public int getUnSoldItemId() {
	return unSoldItemId;
    }

    public void setUnSoldItemId(int unSoldItemId) {
	this.unSoldItemId = unSoldItemId;
    }
}
