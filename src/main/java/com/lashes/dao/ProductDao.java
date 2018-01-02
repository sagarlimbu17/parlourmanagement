package com.lashes.dao;

import com.lashes.entities.Product;
import com.lashes.entities.RgProduct;
import com.lashes.entities.Stock;

import java.util.List;

public interface ProductDao {

    void saveProduct(Product product);
    Long returnLastStock(String productName);
    List<RgProduct> getProductDetailsByCategory(String category);
    List<RgProduct> getProductByProductName(String productName);
    void registerProduct(RgProduct rgProduct);
    RgProduct getSingleProductByProductName(String productName);
    void editSingleProduct(RgProduct rgProduct);
    RgProduct findRgProduct(Long id);
}
