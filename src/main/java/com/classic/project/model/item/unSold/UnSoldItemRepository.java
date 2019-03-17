package com.classic.project.model.item.unSold;

import com.classic.project.model.item.StateOfItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UnSoldItemRepository extends JpaRepository<UnSoldItem, Integer> {

    @Modifying
    @Transactional
    @Query("update UnSoldItem  unSoldItem set unSoldItem.currentValue = ?3, unSoldItem.lastBidder = ?2, unSoldItem.stateOfItem = ?4 where unSoldItem.itemId = ?1")
    void buyNow(int itemId, String userName, double maxPrice, StateOfItem stateOfItem);

    @Modifying
    @Transactional
    @Query("update UnSoldItem  unSoldItem set unSoldItem.currentValue = ?2, unSoldItem.lastBidder = ?3, unSoldItem.stateOfItem = ?4 where unSoldItem.itemId = ?1")
    void bidForItem(int itemId, double newCurrentValue, String lastBidderName, StateOfItem stateOfItem);


    @Query("select unSoldItem from UnSoldItem unSoldItem where unSoldItem.stateOfItem = 0")
    List<UnSoldItem> getSoldUnSoldItemsByStateOfItem();

    @Query("select unSoldItem from UnSoldItem unSoldItem where unSoldItem.stateOfItem = 1")
    List<UnSoldItem> getUnSoldItemsByStateOfItem();

    @Modifying
    @Transactional
    @Query("delete from UnSoldItem where itemId = ?1")
    void deleteCleanUpUnSoldItems(int itemId);
}
