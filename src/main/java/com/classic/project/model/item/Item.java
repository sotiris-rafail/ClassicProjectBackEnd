package com.classic.project.model.item;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column
    private int itemId;
    @Column
    private Grade grade;
    @Column
    private ItemType itemType;
    @Column
    private String photoPath;
    @Column
    private String itemName;
    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date registerDate;
    @Column
    private StateOfItem stateOfItem;
    @Column
    private SaleState saleState;
    @Column
    @ColumnDefault(value = "false")
    private boolean isEditable;

    public Item(Grade grade, ItemType itemType, String photoPath, String itemName, StateOfItem stateOfItem, SaleState saleState, boolean isEditable) {
        this.grade = grade;
        this.itemType = itemType;
        this.photoPath = photoPath;
        this.itemName = itemName;
        this.stateOfItem = stateOfItem;
        this.saleState = saleState;
        this.isEditable = isEditable;
    }

    public Item(int itemId, Grade grade, ItemType itemType, String photoPath, String itemName, StateOfItem stateOfItem, SaleState saleState) {
        this.itemId = itemId;
        this.grade = grade;
        this.itemType = itemType;
        this.photoPath = photoPath;
        this.itemName = itemName;
        this.stateOfItem = stateOfItem;
        this.saleState = saleState;
    }

    public Item(Grade grade, ItemType itemType, String photoPath, String itemName, StateOfItem stateOfItem, SaleState saleState) {
        this.grade = grade;
        this.itemType = itemType;
        this.photoPath = photoPath;
        this.itemName = itemName;
        this.stateOfItem = stateOfItem;
        this.saleState = saleState;
    }

    public Item() {
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public StateOfItem getStateOfItem() {
        return stateOfItem;
    }

    public void setStateOfItem(StateOfItem stateOfItem) {
        this.stateOfItem = stateOfItem;
    }

    public SaleState getSaleState() {
        return saleState;
    }

    public void setSaleState(SaleState saleState) {
        this.saleState = saleState;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", grade=" + grade +
                ", itemType=" + itemType +
                ", photoPath='" + photoPath + '\'' +
                ", itemName='" + itemName + '\'' +
                ", registerDate=" + registerDate +
                ", stateOfItem=" + stateOfItem +
                ", saleState=" + saleState +
                ", isEditable=" + isEditable +
                '}';
    }
}
