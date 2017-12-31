package com.lashes.dao;


import com.lashes.entities.Stock;

import java.util.List;

public interface InventoryDao {

    List<Stock> getAllItems(int firstResult);
    public  int getLastPageNumber();
}
