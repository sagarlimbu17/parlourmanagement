package com.lashes.models;

public class Inventory {

    private String productName;
    private String productCategory;
    private long finalStock;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public long getFinalStock() {
        return finalStock;
    }

    public void setFinalStock(long finalStock) {
        this.finalStock = finalStock;
    }
}
