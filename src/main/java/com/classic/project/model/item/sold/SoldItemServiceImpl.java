package com.classic.project.model.item.sold;

import com.classic.project.model.item.Item;
import com.classic.project.model.item.SaleState;
import com.classic.project.model.item.StateOfItem;
import com.classic.project.model.item.sold.exception.SoldItemNotFoundException;
import com.classic.project.model.item.sold.response.ResponseSoldItem;
import com.classic.project.model.item.unSold.UnSoldItem;
import com.classic.project.model.item.unSold.UnSoldItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Component
public class SoldItemServiceImpl implements SoldItemService {

    @Autowired
    private SoldItemRepository soldItemRepository;

    @Autowired
    private UnSoldItemService unSoldItemService;

    private static Logger logger = LoggerFactory.getLogger(SoldItemServiceImpl.class);

    @Override
    public ResponseEntity<List<ResponseSoldItem>> getSoldItems() {
        List<SoldItem> soldItemsFromDb = soldItemRepository.findAll(new Sort(Sort.Direction.ASC, "registerDate"));
        List<ResponseSoldItem> responseSoldItems = new ArrayList<>();
        for (SoldItem soldItem : soldItemsFromDb) {
            responseSoldItems.add(ResponseSoldItem.convertForDisplay(soldItem));
        }
        responseSoldItems.sort(Comparator.comparing(ResponseSoldItem::isDelivered));
        return new ResponseEntity<>(responseSoldItems, HttpStatus.OK);
    }

    @Override
    public void deliverSoldItem(int itemId, boolean isDelivered) {
        int howManyChanged = soldItemRepository.deliverSoldItem(itemId, isDelivered);
        if (howManyChanged == 0) {
            throw new SoldItemNotFoundException(itemId);
        }
    }

    @Override
    public SoldItem save(SoldItem soldItem) {
        return soldItemRepository.save(soldItem);
    }

    @Override
    public void renewItems(List<Integer> renewItems) {
        List<UnSoldItem> itemsToBERenew = new ArrayList<>();
        renewItems.forEach(item -> {
            Optional<SoldItem> toBeRenew = soldItemRepository.findById(item);
            if (toBeRenew.isPresent()) {
                if (toBeRenew.get().getWhoBoughtIt().equals("")) {
                    logger.info("SOLD ITEM GOING TO BE RESALE" + toBeRenew.get().toString());
                    itemsToBERenew.add(new UnSoldItem(toBeRenew.get().getGrade(), toBeRenew.get().getItemType(),
                            toBeRenew.get().getPhotoPath(), toBeRenew.get().getItemName(),
                            StateOfItem.UNSOLD, toBeRenew.get().getMaxPrice(),
                            toBeRenew.get().getBoughtPrice(), toBeRenew.get().getDaysToStayUnSold(),
                            toBeRenew.get().getBoughtPrice(), toBeRenew.get().getWhoBoughtIt(),
                            toBeRenew.get().getBidStep(), SaleState.RESALE, true));
                }
            }
        });
        unSoldItemService.addNewItemForSaleAgain(itemsToBERenew);
        renewItems.forEach(item -> {
            Optional<SoldItem> toBeRenew = soldItemRepository.findById(item);
            if (toBeRenew.isPresent()) {
                if (toBeRenew.get().getWhoBoughtIt().equals("")) {
                    logger.info("SOLD ITEM WHICH RESALE IS GOING TO BE BE DELETED " + toBeRenew.toString());
                    soldItemRepository.deleteById(item);
                }
            }
        });
    }

    @Override
    public String getPathForMail() {
        return null;
    }

    @Override
    public File getFile(Item unSoldItem) {
        return null;
    }

    @Override
    public String addImage(Item soldItem) {
        return null;
    }

    @Override
    public List<Item> getDistinctItemNames() {
        return soldItemRepository.getDistinctItemNames();
    }
}
