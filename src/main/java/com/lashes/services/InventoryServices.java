package com.lashes.services;


import com.lashes.entities.Stock;

import java.util.List;

public interface InventoryServices {

    List<Stock> getAllItems(int firstResult);
    int getLastPageNumber();
}
