package com.lashes.services;

import com.lashes.dao.ProductDao;
import com.lashes.entities.Product;
import com.lashes.entities.RgProduct;
import com.lashes.entities.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    public void addProduct(Product product) {
        productDao.saveProduct(product);
    }


    public List<RgProduct> getProductDetailsByCategory(String category) {
        return productDao.getProductDetailsByCategory(category);
    }

    public List<RgProduct> getProductByProductName(String productName) {
        return productDao.getProductByProductName(productName);
    }

    public void registerProduct(RgProduct rgProduct){
        productDao.registerProduct(rgProduct);
    }

    public long returnLastStock(String productName){
        return productDao.returnLastStock(productName);
    }

    @Override
    public void editSingleProduct(RgProduct rgProduct) {
        productDao.editSingleProduct(rgProduct);

    }

    @Override
    public RgProduct findRgProduct(Long id) {
        return productDao.findRgProduct(id);
    }
}
