package com.lashes.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Finance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date targetDate;
    private Double targetAmount;
    private Date createdDate;

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public Double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
