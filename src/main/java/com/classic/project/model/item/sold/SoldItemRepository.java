package com.classic.project.model.item.sold;

import com.classic.project.model.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SoldItemRepository extends JpaRepository<SoldItem, Integer> {

    @Modifying
    @Transactional
    @Query("update SoldItem soldItem set soldItem.isDelivered = ?2 where soldItem.itemId = ?1")
    int deliverSoldItem(int itemId, boolean isDelivered);

    @Query("select max(soldItem.itemId) from SoldItem soldItem")
    int maxId();

    @Query("select distinct new com.classic.project.model.item.Item(soldItem.itemName, soldItem.photoPath) from SoldItem soldItem")
    List<Item> getDistinctItemNames();
}
