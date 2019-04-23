package com.classic.project.model.item.scheduler;

import com.classic.project.model.item.StateOfItem;
import com.classic.project.model.item.sold.SoldItem;
import com.classic.project.model.item.sold.SoldItemRepository;
import com.classic.project.model.item.unSold.UnSoldItem;
import com.classic.project.model.item.unSold.UnSoldItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CleanUnSoldItems {

    @Autowired
    private UnSoldItemRepository unSoldItemRepository;

    @Autowired
    private SoldItemRepository soldItemRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Scheduled(cron = "0 0 * * * *") //the top of every hour of every day.
    public void cleanUpTheSoldUnsoldItem() {
        List<UnSoldItem> unSoldItems = unSoldItemRepository.getSoldUnSoldItemsByStateOfItem();
	List<SoldItem> soldItems = new ArrayList<>();
	for(UnSoldItem unSoldItem : unSoldItems){
	    soldItems.add(registerUnSoldItemsAsSoldItems(unSoldItem));
	    deleteUnSoldItems(unSoldItem.getItemId());
	}
	if(!soldItems.isEmpty()) {
	    notifyForSoldItems(soldItems);
	}
    }

    @Scheduled(cron = "0 0 0 * * *") //the top of every hour of every day.
    public void cleanUpExpiredUnSoldItems(){
	List<UnSoldItem> unSoldItems = unSoldItemRepository.getUnSoldItemsByStateOfItem();
	List<SoldItem> soldItems = new ArrayList<>();
	for(UnSoldItem unSoldItem : unSoldItems){
	    if(unSoldItem.getExpirationDate().before(new Date())){
	        unSoldItem.setStateOfItem(StateOfItem.SOLD);
		soldItems.add(registerUnSoldItemsAsSoldItems(unSoldItem));
		deleteUnSoldItems(unSoldItem.getItemId());
	    }
	}
	if(!soldItems.isEmpty()) {
	    notifyForSoldItems(soldItems);
	}
    }

    private void notifyForSoldItems(List<SoldItem> soldItems) {
	SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
	simpleMailMessage.setFrom("inquisitionalliance@gmail.com");
	simpleMailMessage.setTo("vasile.nastasiu@gmail.com");
	simpleMailMessage.setSubject("Item(s) to be delivered");
	simpleMailMessage.setText(getText(soldItems));
	simpleMailMessage.setSentDate(new Date());
	javaMailSender.send(simpleMailMessage);
    }

    private String getText(List<SoldItem> soldItems){
        StringBuilder text = new StringBuilder();
        for(SoldItem soldItem : soldItems){
            text.append(soldItem.getItemName()).append(" has been sold at the price of ").append(soldItem.getBoughtPrice()).
		append(" adena.").append("The winner is ").append(soldItem.getWhoBoughtIt()).append(".\n");
	}
        return text.toString();
    }

    private SoldItem registerUnSoldItemsAsSoldItems(UnSoldItem unSoldItem) {
	SoldItem soldItem = new SoldItem(unSoldItem.getGrade(), unSoldItem.getItemType(), unSoldItem.getPhotoPath(), unSoldItem.getItemName(), unSoldItem.getStateOfItem(), unSoldItem.getMaxPrice(), unSoldItem.getCurrentValue(), unSoldItem.getLastBidder(), false, unSoldItem.getItemId());
	soldItem.setRegisterDate(new Date());
	return soldItemRepository.save(soldItem);
    }

    private void deleteUnSoldItems(int itemId){
        unSoldItemRepository.deleteCleanUpUnSoldItems(itemId);
    }
}
