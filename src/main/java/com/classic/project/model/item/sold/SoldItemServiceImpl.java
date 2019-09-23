package com.classic.project.model.item.sold;

import com.classic.project.model.item.sold.exception.SoldItemNotFoundException;
import com.classic.project.model.item.sold.response.ResponseSoldItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class SoldItemServiceImpl implements SoldItemService {

    @Autowired
    private SoldItemRepository soldItemRepository;

    @Override
    public ResponseEntity<List<ResponseSoldItem>> getSoldItems() {
        List<SoldItem> soldItemsFromDb = soldItemRepository.findAll(new Sort(Sort.Direction.ASC, "registerDate"));
        List<ResponseSoldItem> responseSoldItems = new ArrayList<>();
        for(SoldItem soldItem : soldItemsFromDb) {
            responseSoldItems.add(ResponseSoldItem.convertForDisplay(soldItem));
        }
        responseSoldItems.sort(Comparator.comparing(ResponseSoldItem::isDelivered));
        return new ResponseEntity<>(responseSoldItems, HttpStatus.OK);
    }

    @Override
    public void deliverSoldItem(int itemId, boolean isDelivered) {
	int howManyChanged = soldItemRepository.deliverSoldItem(itemId, isDelivered);
	if(howManyChanged == 0){
	    throw new SoldItemNotFoundException(itemId);
	}
    }

    @Override
    public SoldItem save(SoldItem soldItem) {
        return soldItemRepository.save(soldItem);
    }
}
