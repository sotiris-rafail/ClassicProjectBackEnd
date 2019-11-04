package com.classic.project.model.item.unSold;

import com.classic.project.model.character.Character;
import com.classic.project.model.character.CharacterService;
import com.classic.project.model.character.TypeOfCharacter;
import com.classic.project.model.item.Item;
import com.classic.project.model.item.StateOfItem;
import com.classic.project.model.item.unSold.exception.UnSoldItemsNotFoundException;
import com.classic.project.model.item.unSold.response.EditUnSoldItem;
import com.classic.project.model.item.unSold.response.NewUnSoldItem;
import com.classic.project.model.item.unSold.response.ResponseUnSoldItem;
import com.classic.project.model.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.*;

@Component
public class UnSoldItemServiceImpl implements UnSoldItemService {

    @Autowired
    private UnSoldItemRepository unSoldItemRepository;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    private static final String PHOTO_PATH = "../../assets/itemPhoto/";

    private static final String PHOTO_PATH_FOR_MAIL = "/../ClassicProjectFrontEnd/src/";

    private static final String JPG = ".jpg";

    @Value("${send.email.new.items}")
    private boolean notifyForEachNewItem;

    @Value("${auction.link}")
    private String auctionLink;

    @Autowired
    private Environment environment;

    private static final Logger logger = LoggerFactory.getLogger(UnSoldItemServiceImpl.class);

    public String getPathForMail() {
        return environment.getProperty("user.dir") + PHOTO_PATH_FOR_MAIL;
    }

    @Override
    public void addNewItemForSale(List<NewUnSoldItem> unSoldItems) {
        logger.info("ADDING NEW UNSOLD ITEMS");
        List<UnSoldItem> unSoldItemListForTheMail = new ArrayList<>();
        for(NewUnSoldItem newUnSoldItem : unSoldItems ) {
            for (int i = 0; i < newUnSoldItem.getAmount(); i++) {
                UnSoldItem unSoldItem = NewUnSoldItem.convertToUnSoldItem(newUnSoldItem);
                unSoldItem.setRegisterDate(trimToZero(new Date()));
                unSoldItem.setExpirationDate(getExpirationDate(unSoldItem.getRegisterDate(), unSoldItem.getDaysToStayUnSold()));
                unSoldItem.setMaxPrice(calculatePrices(unSoldItem.getMaxPrice()));
                unSoldItem.setBidStep(calculatePrices(unSoldItem.getBidStep()));
                unSoldItem.setCurrentValue(calculatePrices(unSoldItem.getCurrentValue()));
                unSoldItem.setStartingPrice(calculatePrices(unSoldItem.getStartingPrice()));
                unSoldItem.setPhotoPath(createPhotoPath(unSoldItem.getItemName()));
                unSoldItemListForTheMail.add(unSoldItemRepository.save(unSoldItem));
                logger.info("ADDING UN_SOLD ITEM" + unSoldItem.toString());
                unSoldItemRepository.flush();
            }
        }
        if (notifyForEachNewItem && !unSoldItemListForTheMail.isEmpty()) {
            sendMail(unSoldItemListForTheMail);
        }
    }

    @Override
    public void addNewItemForSaleAgain(List<UnSoldItem> renewItems) {
        renewItems.forEach(item -> {
            item.setRegisterDate(trimToZero(new Date()));
            item.setExpirationDate(getExpirationDate(item.getRegisterDate(), item.getDaysToStayUnSold()));
            logger.info("READING UN_SOLD ITEM" + item.toString());
        });
        unSoldItemRepository.saveAll(renewItems);
    }

    private void sendMail(List<UnSoldItem> unSoldItemListForTheMail) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mail = new MimeMessageHelper(message, true);
            mail.setTo(userService.getUsersEmailWithSendNewItemOptionEnable().toArray(new String[0]));
            mail.setText(getMailBody(unSoldItemListForTheMail), true);
            mail.setSubject("New item(s) on auction");
            addAttachments(mail, unSoldItemListForTheMail);
            mail.setFrom("inquisitionAlliance@gmail.com");
            mail.setSentDate(new Date());
            javaMailSender.send(mail.getMimeMessage());
        } catch (MessagingException e) {
            logger.error("EMAIL FOR ADDING UNSOLD ITEMS FAILED. DID NOT SEND");
            logger.error(e.getMessage());
        }
    }

    private void addAttachments(MimeMessageHelper mail, List<UnSoldItem> unSoldItemListForTheMail) {
        try {
            for (UnSoldItem unSoldItem : unSoldItemListForTheMail) {
                if(new File(Paths.get(getPathForMail() + unSoldItem.getPhotoPath().replace("../../", "")).toString()).exists()) {
                    mail.addInline(unSoldItem.getPhotoPath().replace("../../assets/itemPhoto/", ""), getFile(unSoldItem));
                }
            }
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
    }

    public File getFile(Item unSoldItem) {
        return  new File(Paths.get(getPathForMail() + unSoldItem.getPhotoPath().replace("../../", "")).toString());
    }

    private String getMailBody(List<UnSoldItem> unSoldItemListForTheMail) {
        int x = 0;
        String simpleTD = "<td class=\"td\">";
        String coloredTDFirst = "<td class=\"td color first\">";
        String coloredTD = "<td class=\"td color\">";
        String coloredTDLast = "<td class=\"td color last\">";
        String whichOne;
        String last;
        StringBuilder text = new StringBuilder();
        text.append("<html>");
        text.append("<head><style>");
        text.append(".table{font-family: arial, sans-serif; border-collapse: collapse; width: 100%}");
        text.append(".td, .th, .span{text-align: left; padding: 10px;}");
        text.append(".color {background-color: lightblue;}");
        text.append(".color-th{ background-color: DodgerBlue;}");
        text.append(".first {border-radius: 12px 0 0 12px;}");
        text.append(".last {border-radius: 0 12px 12px 0;}");
        text.append(".a, .span {font-family: arial, sans-serif; font-weight:bold;text-decoration:none;}");
        text.append("</style></head>");
        text.append("<table class=\"table\">");
        text.append("<body>");
        text.append("<tr class=\"tr\">");
        text.append("<th class=\"th color-th first\">Item</th><th class=\"th color-th\">Grade</th><th class=\"th color-th\">Type</th><th class=\"th color-th\">Starting Price</th>" +
                "<th class=\"th color-th\">Bid Step</th><th class=\"th color-th last\">Duration</th>");
        text.append("</tr>");
        for (UnSoldItem unSoldItem : unSoldItemListForTheMail) {
            boolean photo = getFile(unSoldItem).exists();
            text.append("<tr class=\"tr\">");
            if (x % 2 == 0) {
                whichOne = last = simpleTD;
                text.append(simpleTD).append(photo ? addImage(unSoldItem) : unSoldItem.getItemName()).append("</td>");
            } else {
                text.append(coloredTDFirst).append(photo ? addImage(unSoldItem) : unSoldItem.getItemName()).append("</td>");
                whichOne = coloredTD;
                last = coloredTDLast;
            }
            text.append(whichOne).append(unSoldItem.getGrade()).append("</td>")
                    .append(whichOne).append(unSoldItem.getItemType().getType()).append("</td>")
                    .append(whichOne).append(NumberFormat.getIntegerInstance().format(unSoldItem.getStartingPrice())).append("</td>")
                    .append(whichOne).append(NumberFormat.getIntegerInstance().format(unSoldItem.getStartingPrice())).append("</td>")
                    .append(last).append(DateFormat.getDateInstance(2).format(unSoldItem.getExpirationDate())).append(" ")
                    .append(DateFormat.getTimeInstance(1).format(unSoldItem.getExpirationDate())).append("</td>")
                    .append("</tr>");
            x++;
        }
        text.append("</table>");
        text.append("<span class=\"span\">More information can be found in the <a class=\"a\" href=").append(auctionLink).append(">auction</a> system</span>");
        text.append("</body></html>");
        return text.toString();
    }

    public String addImage(Item soldItem) {
        return "<img src=\" cid:"+ soldItem.getPhotoPath().replace(PHOTO_PATH, "") +"\" alt=\""+soldItem.getItemName()+"\"/>";
    }

    @Override
    public List<Item> getDistinctItemNames() {
        return unSoldItemRepository.getDistinctItemNames();
    }


    @Override
    public ResponseEntity<List<ResponseUnSoldItem>> getUnSoldItems() {
        List<UnSoldItem> allUnSoldItems = unSoldItemRepository.findAll();
        List<ResponseUnSoldItem> responseUnSoldItems = new ArrayList<>();
        for (UnSoldItem unSoldItem : allUnSoldItems) {
            if (unSoldItem.getStateOfItem().getState().equals(StateOfItem.UNSOLD.getState())) {
                responseUnSoldItems.add(ResponseUnSoldItem.convertToResponse(unSoldItem));
            }
        }
        if (responseUnSoldItems.isEmpty()) {
            throw new UnSoldItemsNotFoundException("There are not items for sale at the moment");
        }
        responseUnSoldItems.sort(Comparator.comparing(ResponseUnSoldItem::getExpirationDate));
        return new ResponseEntity<>(responseUnSoldItems, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> bidForUnSoldItem(int itemId, int bidStep, int userId) {
        Optional<UnSoldItem> unSoldItem = unSoldItemRepository.findById(itemId);
        if (!unSoldItem.isPresent()) {
            throw new UnSoldItemsNotFoundException(itemId);
        }
        double newCurrentValue = unSoldItem.get().getCurrentValue() + bidStep;
        if (unSoldItem.get().getMaxPrice() < newCurrentValue) {
            unSoldItemRepository.bidForItem(itemId, newCurrentValue, getLastBidderName(userId), StateOfItem.SOLD);
        } else {
            unSoldItemRepository.bidForItem(itemId, newCurrentValue, getLastBidderName(userId), StateOfItem.UNSOLD);
        }
        return ResponseEntity.ok(getLastBidderName(userId));
    }

    @Override
    public void buyNowUnSoldItem(int itemId, int userId) {
        Optional<UnSoldItem> unSoldItem = unSoldItemRepository.findById(itemId);
        if (!unSoldItem.isPresent()) {
            throw new UnSoldItemsNotFoundException(itemId);
        }
        unSoldItemRepository.buyNow(itemId, getLastBidderName(userId), unSoldItem.get().getMaxPrice(), StateOfItem.SOLD);
    }

    @Override
    public ResponseEntity<Integer> getNumberOfUnsoldItems() {
        return new ResponseEntity<>(unSoldItemRepository.countUnSoldItemByStateOfItem(StateOfItem.UNSOLD), HttpStatus.OK);
    }

    @Override
    public void deleteCleanUpUnSoldItems(int itemId) {
        unSoldItemRepository.deleteCleanUpUnSoldItems(itemId);
    }

    @Override
    public List<UnSoldItem> getSoldUnSoldItemsByStateOfItem() {
        return unSoldItemRepository.getSoldUnSoldItemsByStateOfItem();
    }

    @Override
    public List<UnSoldItem> getUnSoldItemsByStateOfItem() {
        return unSoldItemRepository.getUnSoldItemsByStateOfItem();
    }

    @Override
    public void editUnSoldItem(EditUnSoldItem editUnSoldItem) {
        if(editUnSoldItem == null) {
            throw new UnSoldItemsNotFoundException("Edit item is null");
        }
        Optional<UnSoldItem> unSoldItemFromDb = unSoldItemRepository.findById(editUnSoldItem.getItemId());
        unSoldItemFromDb.ifPresent(unSoldItem -> {
            logger.info("ITEM BEFORE EDIT : " + unSoldItemFromDb.get().toString());
            unSoldItemFromDb.get().setItemName(editUnSoldItem.getName());
            unSoldItemFromDb.get().setRegisterDate(trimToZero(new Date()));
            unSoldItemFromDb.get().setExpirationDate(getExpirationDate(unSoldItemFromDb.get().getRegisterDate(), editUnSoldItem.getNumberOfDays()));
            unSoldItemFromDb.get().setMaxPrice(calculatePrices(editUnSoldItem.getMaxPrice()));
            unSoldItemFromDb.get().setBidStep(calculatePrices(editUnSoldItem.getBidStep()));
            unSoldItemFromDb.get().setStartingPrice(calculatePrices(editUnSoldItem.getStartingPrice()));
            unSoldItemFromDb.get().setCurrentValue(calculatePrices(editUnSoldItem.getStartingPrice()));
            unSoldItemFromDb.get().setEditable(false);
            logger.info("ITEM AFTER EDIT : " + unSoldItemFromDb.get().toString());
            unSoldItemRepository.save(unSoldItemFromDb.get());
        });
    }

    private static Date getExpirationDate(Date registerDate, int daysToStayUnSold) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(registerDate);
        calendar.add(Calendar.DATE, daysToStayUnSold);
        calendar.set(Calendar.MILLISECOND, 5);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        return calendar.getTime();
    }

    private String getLastBidderName(int userId) {
        Optional<Character> first = characterService.findByUserId(userId)
                .stream()
                .filter(character -> character.getTypeOfCharacter().equals(
                        TypeOfCharacter.MAIN))
                .findFirst();
        if (first.isPresent()) {
            return first.get().getInGameName();
        } else {
            throw new UnSoldItemsNotFoundException("Can not bid if you do not have a main character registered");
        }
    }

    private static Date trimToZero(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        return calendar.getTime();
    }

    private static double calculatePrices(double price) {
        return price * 1000000;
    }

    private static String createPhotoPath(String itemName) {
        String[] splittedItemName = itemName.trim().split("\\s+");
        if (splittedItemName.length == 1) {
            return PHOTO_PATH + splittedItemName[0].toLowerCase() + JPG;
        } else {
            StringBuilder photoName = new StringBuilder();
            for (String subString : splittedItemName) {
                photoName.append(subString.toLowerCase()).append("_");
            }
            return PHOTO_PATH + photoName.substring(0, photoName.length() - 1) + JPG;
        }
    }
}
