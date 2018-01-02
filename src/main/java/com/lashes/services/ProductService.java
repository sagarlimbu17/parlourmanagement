package com.lashes.services;

import com.lashes.entities.Product;
import com.lashes.entities.RgProduct;
import com.lashes.entities.Stock;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ProductService {

    void addProduct(Product product);
    List<RgProduct> getProductDetailsByCategory(String category);
    List<RgProduct> getProductByProductName(String productName);
    void registerProduct(RgProduct rgProduct);
    long returnLastStock(String productName);
    void editSingleProduct(RgProduct rgProduct);
    RgProduct findRgProduct(Long id);

}
