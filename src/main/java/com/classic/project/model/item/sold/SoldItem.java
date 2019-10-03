package com.classic.project.model.item.sold;

import com.classic.project.model.item.*;

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
    @Column
    private double bidStep;
    @Column
    private int daysToStayUnSold;

    public SoldItem(Grade grade, ItemType itemType, String photoPath, String itemName, StateOfItem stateOfItem, double maxPrice, double boughtPrice, String whoBoughtIt,
                    boolean isDelivered, int unSoldItemId, double bidStep, int daysToStayUnSold, SaleState saleState) {
        super(grade, itemType, photoPath, itemName, stateOfItem, saleState);
        this.maxPrice = maxPrice;
        this.boughtPrice = boughtPrice;
        this.whoBoughtIt = whoBoughtIt;
        this.isDelivered = isDelivered;
        this.unSoldItemId = unSoldItemId;
        this.bidStep = bidStep;
        this.daysToStayUnSold = daysToStayUnSold;
    }

    public SoldItem(int itemId, Grade grade, ItemType itemType, String photoPath, String itemName, StateOfItem stateOfItem, double maxPrice, double boughtPrice,
                    String whoBoughtIt, boolean isDelivered, int unSoldItemId, SaleState saleState) {
        super(itemId, grade, itemType, photoPath, itemName, stateOfItem, saleState);
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

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getBoughtPrice() {
        return boughtPrice;
    }

    public void setBoughtPrice(double boughtPrice) {
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

    public double getBidStep() {
        return bidStep;
    }

    public void setBidStep(double bidStep) {
        this.bidStep = bidStep;
    }

    public int getDaysToStayUnSold() {
        return daysToStayUnSold;
    }

    public void setDaysToStayUnSold(int daysToStayUnSold) {
        this.daysToStayUnSold = daysToStayUnSold;
    }

    @Override
    public String toString() {
        return "SoldItem{" +
                "maxPrice=" + maxPrice +
                ", boughtPrice=" + boughtPrice +
                ", whoBoughtIt='" + whoBoughtIt + '\'' +
                ", isDelivered=" + isDelivered +
                ", unSoldItemId=" + unSoldItemId +
                ", bidStep=" + bidStep +
                ", daysToStayUnSold=" + daysToStayUnSold +
                '}' + super.toString();
    }
}
