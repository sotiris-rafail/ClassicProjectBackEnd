package com.classic.project.model.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/item")
public class ItemController {

    @Autowired
    @Qualifier("itemServiceImpl")
    private ItemService itemService;

    @RequestMapping(path = "/distinctNames", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<Item> getSoldItems() {
        return itemService.getDistinctItemNames();
    }
}
