package com.lashes.dao;

import com.lashes.entities.Product;
import com.lashes.entities.RgProduct;
import com.lashes.entities.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    @PersistenceContext
    EntityManager em;


    public void saveProduct(Product product) {

        RgProduct rgProduct = getSingleProductByProductName(product.getProductName());
        Long finalStock = returnLastStock(product.getProductName())+product.getProductQuantity();

        Stock stock = new Stock();
        stock.setProductId(rgProduct.getId());
        stock.setProductName(product.getProductName());
        stock.setFinalStock(finalStock);
        rgProduct.setFinalStock(finalStock);
        em.merge(rgProduct);
        em.persist(stock);
        em.persist(product);
    }

    public Long returnLastStock(String prodName) {
        Long stock=0L;
        Query query = em
                .createNativeQuery("SELECT * FROM Stock s WHERE s.productName=:prodName ORDER BY  stockId DESC  LIMIT  1 ",Stock.class);
        query.setParameter("prodName",prodName);
        List<Stock> stocks =query.getResultList();
        if(stocks.size()==0){
           return stock;
        }else {
            Stock lastStockObj = stocks.get(stocks.size() - 1);
            stock = lastStockObj.getFinalStock();
        }
        return stock;
    }

    public RgProduct getSingleProductByProductName(String productName) {
        Query query = em.createQuery("select  p from RgProduct p where p.productName=:productName")
                .setParameter("productName",productName);
        RgProduct rgProduct = (RgProduct) query.getSingleResult();
        return rgProduct;
    }

    public List<RgProduct> getProductDetailsByCategory(String category) {

        List<RgProduct> productList = em
                .createQuery("select p from RgProduct p where p.productCategory=:category")
                .setParameter("category",category)
                .getResultList();
        return productList;
    }

    public List<RgProduct> getProductByProductName(String productName) {
        Query query = em.createQuery("select  p from RgProduct p where p.productName=:productName")
                .setParameter("productName",productName);
        List<RgProduct> rgProduct = query.getResultList();
        return rgProduct;
    }

    public void registerProduct(RgProduct rgProduct){
        em.persist(rgProduct);
    }

    public void editSingleProduct(RgProduct rgProduct) {
        em.merge(rgProduct);
    }

    public RgProduct findRgProduct(Long id) {
        RgProduct rgProduct = em.find(RgProduct.class,id);
        return rgProduct;
    }
}
