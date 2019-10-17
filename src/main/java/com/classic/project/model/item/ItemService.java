package com.classic.project.model.item;

import java.io.File;

public interface ItemService {

    String getPathForMail();

    File getFile(Item unSoldItem);

    String addImage(Item soldItem);
}
