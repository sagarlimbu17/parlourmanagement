package com.lashes.dao;


import com.lashes.entities.RgProduct;
import com.lashes.entities.Sales;
import com.lashes.entities.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional
@Repository
public class SalesDaoImpl implements SalesDao{


    @PersistenceContext
    EntityManager em;

    @Autowired
    private ProductDao productDao;

    public void createBill(List<Sales> salesList) {

        for(Sales s:salesList){
            Long lastStockCount = productDao.returnLastStock(s.getName());
            Long newStockCount = lastStockCount-s.getQuantity();

            RgProduct rgProduct = productDao.getSingleProductByProductName(s.getName());

            Double totalCostPrice= rgProduct.getCostPrice() * s.getQuantity();
            s.setTotalCostPrice(totalCostPrice);

            Stock stock = new Stock();
            stock.setProductName(s.getName());
            stock.setFinalStock(newStockCount);
            stock.setProductId(rgProduct.getId());

            rgProduct.setFinalStock(newStockCount);

            em.merge(rgProduct);
            em.persist(stock);
            em.persist(s);
        }
    }

    public void createServiceBill(Sales sales) {
        em.persist(sales);
    }
}
