package com.classic.project.model.item.unSold;

import com.classic.project.model.item.unSold.response.NewUnSoldItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(path = "/unsold")
public class UnSoldItemController {

    private UnSoldItemService unSoldItemService;

    @Autowired
    public UnSoldItemController(UnSoldItemService unSoldItemService) {
        this.unSoldItemService = unSoldItemService;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void addNewUnSoldItem(@RequestBody NewUnSoldItem unSoldItem, @PathParam("amountOfItem") int amountOfItem){
        unSoldItemService.addNewItemForSale(unSoldItem, amountOfItem);
    }
}
