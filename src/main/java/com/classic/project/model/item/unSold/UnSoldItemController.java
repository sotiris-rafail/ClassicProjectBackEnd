package com.classic.project.model.item.unSold;

import com.classic.project.model.item.unSold.response.EditUnSoldItem;
import com.classic.project.model.item.unSold.response.NewUnSoldItem;
import com.classic.project.model.item.unSold.response.ResponseUnSoldItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

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
    public void addNewUnSoldItem(@RequestBody List<NewUnSoldItem> unSoldItems) {
        unSoldItemService.addNewItemForSale(unSoldItems);
    }

    @RequestMapping(path = "/getUnSoldItems", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ResponseUnSoldItem>> getUnSoldItems() {
        return unSoldItemService.getUnSoldItems();
    }

    @RequestMapping(path = "/bidForItem", method = RequestMethod.PUT, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> bidForUnSoldItem(@PathParam("itemId") int itemId, @PathParam("bidStep") int bidStep, @PathParam("userId") int userId) {
        return unSoldItemService.bidForUnSoldItem(itemId, bidStep, userId);
    }

    @RequestMapping(path = "/buyNow", method = RequestMethod.PUT, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void buyNowUnSoldItem(@PathParam("itemId") int itemId, @PathParam("userId") int userId) {
        unSoldItemService.buyNowUnSoldItem(itemId, userId);
    }

    @RequestMapping(path = "/getNumberOfUnsoldItems", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> getNumberOfUnsoldItems() {
        return unSoldItemService.getNumberOfUnsoldItems();
    }

    @RequestMapping(path = "/edit", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void editUnSoldItem(@RequestBody EditUnSoldItem editUnSoldItem) {
        unSoldItemService.editUnSoldItem(editUnSoldItem);
    }
}
