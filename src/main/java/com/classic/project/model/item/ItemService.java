package com.classic.project.model.item;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ItemService {

    String getPathForMail();

    File getFile(Item unSoldItem);

    String addImage(Item soldItem);

    List<Item> getDistinctItemNames();
}
