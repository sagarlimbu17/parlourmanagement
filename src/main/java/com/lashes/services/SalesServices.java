package com.lashes.services;

import com.lashes.entities.Sales;

import java.util.List;

public interface SalesServices {

    void createBill(List<Sales> salesList);
    void createServiceBill(Sales sales);
}
