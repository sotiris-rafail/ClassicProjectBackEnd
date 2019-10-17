package com.classic.project.model.item.unSold;

import com.classic.project.model.item.ItemService;
import com.classic.project.model.item.unSold.response.EditUnSoldItem;
import com.classic.project.model.item.unSold.response.NewUnSoldItem;
import com.classic.project.model.item.unSold.response.ResponseUnSoldItem;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UnSoldItemService extends ItemService {
    void addNewItemForSale(List<NewUnSoldItem> unSoldItems);

    void addNewItemForSaleAgain(List<UnSoldItem> renewItems);

    ResponseEntity<List<ResponseUnSoldItem>> getUnSoldItems();

    ResponseEntity<String> bidForUnSoldItem(int itemId, int bidStep, int userId);

    void buyNowUnSoldItem(int itemId, int userId);

    ResponseEntity<Integer> getNumberOfUnsoldItems();

    void deleteCleanUpUnSoldItems(int itemId);

    List<UnSoldItem> getSoldUnSoldItemsByStateOfItem();

    List<UnSoldItem> getUnSoldItemsByStateOfItem();

    void editUnSoldItem(EditUnSoldItem editUnSoldItem);
}
