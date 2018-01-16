package com.lashes.entities;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity

public class RgProduct {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "name cannot be empty")
    @Column(unique = true)
    private String productName;

    @NotEmpty(message = "category cannot be empty")
    private String productCategory;

    @Min(value = 1, message = "please enter valid cost price")
    @NotNull(message = "please enter valid cost price")
    private Double costPrice;


    @Min(value = 1, message = "please enter valid sellling price")
    @NotNull(message = "please enter valid selling price")
    private Double sellingPrice;

    private Long finalStock;

    @NotEmpty(message = "vedor name required")
    private String productVendor;

    public RgProduct() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getProductVendor() {
        return productVendor;
    }

    public void setProductVendor(String productVendor) {
        this.productVendor = productVendor;
    }

    public Long getFinalStock() {
        return finalStock;
    }

    public void setFinalStock(Long finalStock) {
        this.finalStock = finalStock;
    }
}
