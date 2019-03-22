package com.classic.project.model.item.unSold;

import com.classic.project.model.item.unSold.response.NewUnSoldItem;
import com.classic.project.model.item.unSold.response.ResponseUnSoldItem;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UnSoldItemService {
    void addNewItemForSale(NewUnSoldItem raidBoss, int amountOfItem);

    ResponseEntity<List<ResponseUnSoldItem>> getUnSoldItems();

    ResponseEntity<String> bidForUnSoldItem(int itemId, int bidStep, int userId);

    void buyNowUnSoldItem(int itemId, int userId);

    ResponseEntity<Integer> getNumberOfUnsoldItems();
}
