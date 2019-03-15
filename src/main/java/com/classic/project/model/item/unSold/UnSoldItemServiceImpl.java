package com.classic.project.model.item.unSold;

import com.classic.project.model.item.unSold.response.NewUnSoldItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class UnSoldItemServiceImpl implements UnSoldItemService {

    @Autowired
    private UnSoldItemRepository unSoldItemRepository;

    @Override
    public void addNewItemForSale(NewUnSoldItem newUnSoldItem, int amountOfItem) {
        for (int i = 0; i < amountOfItem; i++) {
            UnSoldItem unSoldItem = NewUnSoldItem.convertToUnSoldItem(newUnSoldItem);
            unSoldItem.setRegisterDate(new Date());
            unSoldItem.setExpirationDate(getExpirationDate(unSoldItem.getRegisterDate(), unSoldItem.getDaysToStayUnSold()));
            unSoldItemRepository.save(unSoldItem);
            unSoldItemRepository.flush();
        }
    }

    private static Date getExpirationDate(Date registerDate, int daysToStayUnSold){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(registerDate);
        calendar.add(Calendar.DATE, daysToStayUnSold);
        return  calendar.getTime();
    }
}
