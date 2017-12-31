package com.lashes.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int salesId;
    private String category,name,vendor;

    private int  quantity;
    private Double totalPrice;

    private String salesType;

    private Double totalCostPrice;

    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @PrePersist
    protected void onCreate() {
        createdDate = new Date();
    }




    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    public Double getTotalCostPrice() {
        return totalCostPrice;
    }

    public void setTotalCostPrice(Double totalCostPrice) {
        this.totalCostPrice = totalCostPrice;
    }

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    public Sales(int salesId,String category, String name, String vendor, int quantity, Double totalPrice, String salesType, Date createdDate) {
        this.salesId=salesId;
        this.category = category;
        this.name = name;
        this.vendor = vendor;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.salesType = salesType;
        this.createdDate = createdDate;
    }

    public Sales() {
    }
}
