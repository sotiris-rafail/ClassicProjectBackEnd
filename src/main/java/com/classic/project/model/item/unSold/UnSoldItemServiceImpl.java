package com.classic.project.model.item.unSold;

import com.classic.project.model.character.Character;
import com.classic.project.model.character.CharacterRepository;
import com.classic.project.model.character.TypeOfCharacter;
import com.classic.project.model.item.StateOfItem;
import com.classic.project.model.item.unSold.exception.UnSoldItemsNotFoundException;
import com.classic.project.model.item.unSold.response.NewUnSoldItem;
import com.classic.project.model.item.unSold.response.ResponseUnSoldItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UnSoldItemServiceImpl implements UnSoldItemService {

    @Autowired
    private UnSoldItemRepository unSoldItemRepository;

    @Autowired
    private CharacterRepository characterRepository;

    private static final String PHOTO_PATH ="../../assets/itemPhoto/";

    private static final String JPG =".jpg";

    @Override
    public void addNewItemForSale(NewUnSoldItem newUnSoldItem, int amountOfItem) {
        for (int i = 0; i < amountOfItem; i++) {
            UnSoldItem unSoldItem = NewUnSoldItem.convertToUnSoldItem(newUnSoldItem);
            unSoldItem.setRegisterDate(trimToZero(new Date()));
            unSoldItem.setExpirationDate(getExpirationDate(unSoldItem.getRegisterDate(), unSoldItem.getDaysToStayUnSold()));
            unSoldItem.setMaxPrice(calculatePrices(unSoldItem.getMaxPrice()));
            unSoldItem.setBidStep(calculatePrices(unSoldItem.getBidStep()));
            unSoldItem.setCurrentValue(calculatePrices(unSoldItem.getCurrentValue()));
            unSoldItem.setStartingPrice(calculatePrices(unSoldItem.getStartingPrice()));
            unSoldItem.setPhotoPath(createPhotoPath(unSoldItem.getItemName()));
            unSoldItemRepository.save(unSoldItem);
            unSoldItemRepository.flush();
        }
    }

    @Override
    public ResponseEntity<List<ResponseUnSoldItem>> getUnSoldItems() {
	List<UnSoldItem> allUnSoldItems = unSoldItemRepository.findAll();
	List<ResponseUnSoldItem> responseUnSoldItems = new ArrayList<>();
	for(UnSoldItem unSoldItem : allUnSoldItems) {
	    if(unSoldItem.getStateOfItem().getState().equals(StateOfItem.UNSOLD.getState())){
	        responseUnSoldItems.add(ResponseUnSoldItem.convertToResponse(unSoldItem));
	    }
	}
	if(responseUnSoldItems.isEmpty()){
	    throw new UnSoldItemsNotFoundException("There are not items for sale at the moment");
	}
	return new ResponseEntity<>(responseUnSoldItems, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> bidForUnSoldItem(int itemId, int bidStep, int userId) {
	Optional<UnSoldItem> unSoldItem = unSoldItemRepository.findById(itemId);
	if(!unSoldItem.isPresent()) {
	    throw new UnSoldItemsNotFoundException(itemId);
	}
	double newCurrentValue = unSoldItem.get().getCurrentValue() + bidStep;
	if(unSoldItem.get().getMaxPrice() < newCurrentValue){
	    unSoldItemRepository.bidForItem(itemId, newCurrentValue, getLastBidderName(userId), StateOfItem.SOLD);
	} else {
	    unSoldItemRepository.bidForItem(itemId, newCurrentValue, getLastBidderName(userId), StateOfItem.UNSOLD);
	}
	return ResponseEntity.ok(getLastBidderName(userId));
    }

    @Override
    public void buyNowUnSoldItem(int itemId, int userId) {
	Optional<UnSoldItem> unSoldItem = unSoldItemRepository.findById(itemId);
	if(!unSoldItem.isPresent()) {
	    throw new UnSoldItemsNotFoundException(itemId);
	}
	unSoldItemRepository.buyNow(itemId, getLastBidderName(userId), unSoldItem.get().getMaxPrice() , StateOfItem.SOLD);
    }

    private static Date getExpirationDate(Date registerDate, int daysToStayUnSold){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(registerDate);
        calendar.add(Calendar.DATE, daysToStayUnSold);
	calendar.set(Calendar.MILLISECOND, 5);
	calendar.set(Calendar.SECOND, 59);
	calendar.set(Calendar.MINUTE, 59);
	calendar.set(Calendar.HOUR_OF_DAY, 23);
        return  calendar.getTime();
    }

    private String getLastBidderName(int userId) {
	Optional<Character> first = characterRepository.findByUserId(userId)
	    .stream()
	    .filter(character -> character.getTypeOfCharacter().equals(
		TypeOfCharacter.MAIN))
	    .findFirst();
	if(first.isPresent()) {
	    return first.get().getInGameName();
	} else {
	    throw new UnSoldItemsNotFoundException("Can not bid if you do not have a main character registered");
	}
    }

    private static Date trimToZero(Date now){
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
        if(splittedItemName.length == 1) {
            return PHOTO_PATH+splittedItemName[0].toLowerCase()+JPG;
	} else {
            StringBuilder photoName = new StringBuilder();
            for(String subString : splittedItemName){
                photoName.append(subString.toLowerCase()).append("_");
	    }
	    return PHOTO_PATH+photoName.substring(0,photoName.length() - 1)+JPG;
	}
    }
}
