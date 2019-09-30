package com.classic.project.model.item.scheduler;

import com.classic.project.model.item.StateOfItem;
import com.classic.project.model.item.sold.SoldItem;
import com.classic.project.model.item.sold.SoldItemService;
import com.classic.project.model.item.unSold.UnSoldItem;
import com.classic.project.model.item.unSold.UnSoldItemService;
import com.classic.project.model.user.User;
import com.classic.project.model.user.UserService;
import com.classic.project.model.user.option.Option;
import com.classic.project.model.user.verification.Verification;
import com.classic.project.model.user.verification.VerificationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CleanUnSoldItems {

    @Autowired
    private UnSoldItemService unSoldItemService;

    @Autowired
    private SoldItemService soldItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${send.email.sold.items}")
    private boolean notifyOnSoldItem;

    @Scheduled(cron = "0 0 * * * *") //the top of every hour of every day.
    public void cleanUpTheSoldUnsoldItem() {
        List<UnSoldItem> unSoldItems = unSoldItemService.getSoldUnSoldItemsByStateOfItem();
        List<SoldItem> soldItems = new ArrayList<>();
        for (UnSoldItem unSoldItem : unSoldItems) {
            soldItems.add(registerUnSoldItemsAsSoldItems(unSoldItem));
            deleteUnSoldItems(unSoldItem.getItemId());
        }
        sendMail(soldItems);
    }

    @Scheduled(cron = "0 0 15 * * *") //every day at 15:00
    public void cleanUpExpiredUnSoldItems() {
        List<UnSoldItem> unSoldItems = unSoldItemService.getUnSoldItemsByStateOfItem();
        List<SoldItem> soldItems = new ArrayList<>();
        for (UnSoldItem unSoldItem : unSoldItems) {
            if (unSoldItem.getExpirationDate().before(new Date())) {
                unSoldItem.setStateOfItem(StateOfItem.SOLD);
                soldItems.add(registerUnSoldItemsAsSoldItems(unSoldItem));
                deleteUnSoldItems(unSoldItem.getItemId());
            }
        }
        sendMail(soldItems);
    }

    private void sendMail(List<SoldItem> soldItems) {
        if (notifyOnSoldItem && !soldItems.isEmpty()) {
            notifyForSoldItems(soldItems);
            notifyUserForSoldItems(soldItems);
        }
    }

    private void notifyForSoldItems(List<SoldItem> soldItems) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("inquisitionalliance@gmail.com");
        simpleMailMessage.setTo(superUserEmails());
        simpleMailMessage.setSubject("Item(s) to be delivered");
        simpleMailMessage.setText(getText(soldItems, false));
        simpleMailMessage.setSentDate(new Date());
        javaMailSender.send(simpleMailMessage);
    }

    private void notifyUserForSoldItems(List<SoldItem> soldItems) {
        List<String> buyers = getDistinctBuyers(soldItems.stream().filter(soldItem -> !soldItem.getWhoBoughtIt().equals("")).collect(Collectors.toList()));
        for (String buyer : buyers) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("inquisitionalliance@gmail.com");
            simpleMailMessage.setTo(userService.findEmailByCharacterName(buyer));
            simpleMailMessage.setSubject("Item(s) bought from auction");
            simpleMailMessage.setText(getText(soldItems.stream().filter(soldItem -> soldItem.getWhoBoughtIt().equals(buyer)).collect(Collectors.toList()), true));
            simpleMailMessage.setSentDate(new Date());
            javaMailSender.send(simpleMailMessage);
        }
    }

    private String getText(List<SoldItem> soldItems, boolean singleMail) {
        StringBuilder text = new StringBuilder();
        for (SoldItem soldItem : soldItems) {
            text.append(soldItem.getItemName()).append(" has been sold at the price of ").append(soldItem.getBoughtPrice()).
                    append(" adena.");
            if (!singleMail) {
                text.append("The winner is ").append(soldItem.getWhoBoughtIt()).append(".\n");
            }
        }
        return text.toString();
    }

    private SoldItem registerUnSoldItemsAsSoldItems(UnSoldItem unSoldItem) {
        SoldItem soldItem = new SoldItem(unSoldItem.getGrade(), unSoldItem.getItemType(), unSoldItem.getPhotoPath(), unSoldItem.getItemName(), unSoldItem.getStateOfItem(), unSoldItem.getMaxPrice(), unSoldItem.getCurrentValue(), unSoldItem.getLastBidder(), false, unSoldItem.getItemId());
        soldItem.setRegisterDate(new Date());
        return soldItemService.save(soldItem);
    }

    private void deleteUnSoldItems(int itemId) {
        unSoldItemService.deleteCleanUpUnSoldItems(itemId);
    }

    private String[] superUserEmails() {
        return userService.getSuperUsersEmail().toArray(new String[0]);
    }

    private List<String> getDistinctBuyers(List<SoldItem> soldItems) {
        List<String> buyers = new ArrayList<>();
        if (soldItems.size() == 1) {
            buyers.add(soldItems.get(0).getWhoBoughtIt());
        } else {
            for (SoldItem item : soldItems) {
                if (!buyers.contains(item.getWhoBoughtIt())) {
                    buyers.add(item.getWhoBoughtIt());
                }
            }
        }
        buyers = findBuyersWithNotificationForSoldItems(buyers);
        return buyers;
    }

    private List<String> findBuyersWithNotificationForSoldItems(List<String> buyers) {
        List<String> finalBuyers = new ArrayList<>();
        for (String buyer : buyers) {
            User user = userService.findUserByCharacterName(buyer);
            if (user.getVerification().getStatus().equals(VerificationStatus.VERIFIED)
                    && user.getOptions().isSoldItemOption()) {
                finalBuyers.add(buyer);
            }
        }
        return finalBuyers;
    }
}
