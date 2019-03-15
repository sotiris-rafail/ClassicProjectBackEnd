package com.classic.project.model.item.sold;

import com.classic.project.model.item.sold.response.ResponseSoldItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(path = "/sold")
public class SoldItemController {

    private SoldItemService soldItemService;

    @Autowired
    public SoldItemController(SoldItemService soldItemService) {
        this.soldItemService = soldItemService;
    }

    @RequestMapping(path = "/getSoldItems", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ResponseSoldItem>> getSoldItems() {
        return soldItemService.getSoldItems();
    }

    @RequestMapping(path = "/delivery", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void getSoldItems(@PathParam("itemId")int itemId, @PathParam("isDelivered")boolean isDelivered) {
        soldItemService.deliverSoldItem(itemId, isDelivered);
    }
}
