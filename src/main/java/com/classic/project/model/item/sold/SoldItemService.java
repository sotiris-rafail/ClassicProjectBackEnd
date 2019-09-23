package com.classic.project.model.item.sold;

import com.classic.project.model.item.sold.response.ResponseSoldItem;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SoldItemService {
    ResponseEntity<List<ResponseSoldItem>> getSoldItems();

    void deliverSoldItem(int itemId, boolean isDelivered);

    SoldItem save(SoldItem soldItem);
}
