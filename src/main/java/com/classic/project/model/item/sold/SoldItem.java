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
    private int maxPrice;
    @Column
    private int boughtPrice;
    @Column
    private String whoBoughtIt;
    @Column
    private boolean isDelivered;

    public SoldItem(Grade grade, ItemType itemType, String photoPath, String itemName, StateOfItem stateOfItem, int maxPrice, int boughtPrice, String whoBoughtIt, boolean isDelivered) {
        super(grade, itemType, photoPath, itemName, stateOfItem);
        this.maxPrice = maxPrice;
        this.boughtPrice = boughtPrice;
        this.whoBoughtIt = whoBoughtIt;
        this.isDelivered = isDelivered;
    }

    public SoldItem(int itemId, Grade grade, ItemType itemType, String photoPath, String itemName, StateOfItem stateOfItem, int maxPrice, int boughtPrice, String whoBoughtIt, boolean isDelivered) {
        super(itemId, grade, itemType, photoPath, itemName, stateOfItem);
        this.maxPrice = maxPrice;
        this.boughtPrice = boughtPrice;
        this.whoBoughtIt = whoBoughtIt;
        this.isDelivered = isDelivered;
    }

    public SoldItem(){
        super();
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getBoughtPrice() {
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
}
