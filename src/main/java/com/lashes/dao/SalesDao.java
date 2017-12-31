package com.lashes.dao;

import com.lashes.entities.RgProduct;
import com.lashes.entities.Sales;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface SalesDao {

    void createBill(List<Sales> sales);
    void createServiceBill(Sales sales);
}
