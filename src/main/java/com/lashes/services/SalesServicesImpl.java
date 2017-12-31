package com.lashes.services;

import com.lashes.dao.SalesDao;
import com.lashes.entities.Sales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesServicesImpl implements SalesServices {


    @Autowired
    private SalesDao salesDao;

    public void createBill(List<Sales> salesList) {
        salesDao.createBill(salesList);
    }

    public void createServiceBill(Sales sales) {
        salesDao.createServiceBill(sales);
    }
}
