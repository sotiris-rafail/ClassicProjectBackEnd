package com.classic.project.model.item.unSold;

import com.classic.project.model.item.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Table
@Entity
public class UnSoldItem extends Item {

    @Column
    private double maxPrice;
    @Column
    private double startingPrice;
    @Column
    private int daysToStayUnSold;
    @Column
    private double currentValue;
    @Column
    private String lastBidder;
    @Column
    private double bidStep;
    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date expirationDate;

    public UnSoldItem(Grade grade, ItemType itemType, String photoPath, String itemName, StateOfItem stateOfItem, double maxPrice, double startingPrice,
                      int daysToStayUnSold, double currentValue, String lastBidder, double bidStep, SaleState saleState, boolean isEditable) {
        super(grade, itemType, photoPath, itemName, stateOfItem, saleState, isEditable);
        this.maxPrice = maxPrice;
        this.startingPrice = startingPrice;
        this.daysToStayUnSold = daysToStayUnSold;
        this.currentValue = currentValue;
        this.lastBidder = lastBidder;
        this.bidStep = bidStep;
    }

    public UnSoldItem(int itemId, Grade grade, ItemType itemType, String photoPath, String itemName, StateOfItem stateOfItem, double maxPrice, double startingPrice,
                      int daysToStayUnSold, double currentValue, String lastBidder, double bidStep, Date expirationDate, SaleState saleState) {
        super(itemId, grade, itemType, photoPath, itemName, stateOfItem, saleState);
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

    public int getDaysToStayUnSold() {
        return daysToStayUnSold;
    }

    public void setDaysToStayUnSold(int daysToStayUnSold) {
        this.daysToStayUnSold = daysToStayUnSold;
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

    public double getBidStep() {
        return bidStep;
    }

    public void setBidStep(double bidStep) {
        this.bidStep = bidStep;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "UnSoldItem{" +
                "maxPrice=" + maxPrice +
                ", startingPrice=" + startingPrice +
                ", daysToStayUnSold=" + daysToStayUnSold +
                ", currentValue=" + currentValue +
                ", lastBidder='" + lastBidder + '\'' +
                ", bidStep=" + bidStep +
                ", expirationDate=" + expirationDate +
                '}' + super.toString();
    }
}
