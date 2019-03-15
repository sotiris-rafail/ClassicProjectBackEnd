package com.classic.project.model.item.unSold;

import com.classic.project.model.item.unSold.response.NewUnSoldItem;

public interface UnSoldItemService {
    void addNewItemForSale(NewUnSoldItem raidBoss, int amountOfItem);
}
