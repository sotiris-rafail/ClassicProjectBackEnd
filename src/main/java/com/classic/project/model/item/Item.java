package com.classic.project.model.item;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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

    public Item(int itemId, Grade grade, ItemType itemType, String photoPath, String itemName, StateOfItem stateOfItem) {
        this.itemId = itemId;
        this.grade = grade;
        this.itemType = itemType;
        this.photoPath = photoPath;
        this.itemName = itemName;
        this.stateOfItem = stateOfItem;
    }

    public Item(Grade grade, ItemType itemType, String photoPath, String itemName, StateOfItem stateOfItem) {
        this.grade = grade;
        this.itemType = itemType;
        this.photoPath = photoPath;
        this.itemName = itemName;
        this.stateOfItem = stateOfItem;
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
}
