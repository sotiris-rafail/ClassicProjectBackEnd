package com.classic.project.model.item;

import com.classic.project.model.item.sold.SoldItemService;
import com.classic.project.model.item.unSold.UnSoldItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ItemServiceImpl implements ItemService {

    @Autowired
    private SoldItemService soldItemService;

    @Autowired
    private UnSoldItemService unSoldItemService;

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
        List<Item> itemNames = new ArrayList<>(soldItemService.getDistinctItemNames());
        for(Item item : unSoldItemService.getDistinctItemNames()) {
            if(!itemNames.contains(item)) {
                itemNames.add(item);
            }
        }
        return itemNames;
    }
}
