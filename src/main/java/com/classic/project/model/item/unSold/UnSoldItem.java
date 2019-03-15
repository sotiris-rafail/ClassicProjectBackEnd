package com.classic.project.model.item.unSold;

import com.classic.project.model.item.Grade;
import com.classic.project.model.item.Item;
import com.classic.project.model.item.ItemType;
import com.classic.project.model.item.StateOfItem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Table
@Entity
public class UnSoldItem extends Item {

    @Column
    private int maxPrice;
    @Column
    private int startingPrice;
    @Column
    private int daysToStayUnSold;
    @Column
    private int currentValue;
    @Column
    private String lastBidder;
    @Column
    private int bidStep;
    @Column
    private Date expirationDate;

    public UnSoldItem(Grade grade, ItemType itemType, String photoPath, String itemName, StateOfItem stateOfItem, int maxPrice, int startingPrice, int daysToStayUnSold, int currentValue, String lastBidder, int bidStep) {
        super(grade, itemType, photoPath, itemName, stateOfItem);
        this.maxPrice = maxPrice;
        this.startingPrice = startingPrice;
        this.daysToStayUnSold = daysToStayUnSold;
        this.currentValue = currentValue;
        this.lastBidder = lastBidder;
        this.bidStep = bidStep;
    }

    public UnSoldItem(int itemId, Grade grade, ItemType itemType, String photoPath, String itemName, StateOfItem stateOfItem, int maxPrice, int startingPrice, int daysToStayUnSold, int currentValue, String lastBidder, int bidStep, Date expirationDate) {
        super(itemId, grade, itemType, photoPath, itemName, stateOfItem);
        this.maxPrice = maxPrice;
        this.startingPrice = startingPrice;
        this.daysToStayUnSold = daysToStayUnSold;
        this.currentValue = currentValue;
        this.lastBidder = lastBidder;
        this.bidStep = bidStep;
        this.expirationDate = expirationDate;
    }

    public UnSoldItem(){
        super();
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(int startingPrice) {
        this.startingPrice = startingPrice;
    }

    public int getDaysToStayUnSold() {
        return daysToStayUnSold;
    }

    public void setDaysToStayUnSold(int daysToStayUnSold) {
        this.daysToStayUnSold = daysToStayUnSold;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public String getLastBidder() {
        return lastBidder;
    }

    public void setLastBidder(String lastBidder) {
        this.lastBidder = lastBidder;
    }

    public int getBidStep() {
        return bidStep;
    }

    public void setBidStep(int bidStep) {
        this.bidStep = bidStep;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
