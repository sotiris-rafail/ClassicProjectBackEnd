package com.classic.project.model.item.scheduler;

import com.classic.project.model.item.sold.SoldItem;
import com.classic.project.model.item.sold.SoldItemRepository;
import com.classic.project.model.item.unSold.UnSoldItem;
import com.classic.project.model.item.unSold.UnSoldItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class CleanUnSoldItems {

    @Autowired
    private UnSoldItemRepository unSoldItemRepository;

    @Autowired
    private SoldItemRepository soldItemRepository;

    @Scheduled(cron = "0 0 * * * *") //the top of every hour of every day.
    public void cleanUpTheSoldUnsoldItem() {
        List<UnSoldItem> unSoldItems = unSoldItemRepository.getSoldUnSoldItemsByStateOfItem();
	for(UnSoldItem unSoldItem : unSoldItems){
	    registerUnSoldItemsAsSoldItems(unSoldItem);
	    deleteUnSoldItems(unSoldItem.getItemId());
	}
    }

    @Scheduled(cron = "0 0 * * * *") //the top of every hour of every day.
    public void cleanUpExpiredUnSoldItems(){
	List<UnSoldItem> unSoldItems = unSoldItemRepository.getUnSoldItemsByStateOfItem();
	for(UnSoldItem unSoldItem : unSoldItems){
	    if(unSoldItem.getExpirationDate().before(new Date())){
		registerUnSoldItemsAsSoldItems(unSoldItem);
		deleteUnSoldItems(unSoldItem.getItemId());
	    }
	}
    }

    private void registerUnSoldItemsAsSoldItems(UnSoldItem unSoldItem) {
	SoldItem soldItem = new SoldItem(unSoldItem.getItemId(), unSoldItem.getGrade(), unSoldItem.getItemType(), unSoldItem.getPhotoPath(), unSoldItem.getItemName(), unSoldItem.getStateOfItem(), unSoldItem.getMaxPrice(), unSoldItem.getCurrentValue(), unSoldItem.getLastBidder(), false);
	soldItem.setRegisterDate(new Date());
	soldItemRepository.save(soldItem);
    }

    private void deleteUnSoldItems(int itemId){
        unSoldItemRepository.deleteCleanUpUnSoldItems(itemId);
    }
}
