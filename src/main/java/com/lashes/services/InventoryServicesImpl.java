package com.lashes.services;

import com.lashes.dao.InventoryDao;
import com.lashes.entities.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServicesImpl  implements InventoryServices{

    @Autowired
    private InventoryDao inventoryDao;


    public List<Stock> getAllItems(int firstResult) {
        return inventoryDao.getAllItems(firstResult);
    }

    public  int getLastPageNumber(){
        return inventoryDao.getLastPageNumber();
    }
}
