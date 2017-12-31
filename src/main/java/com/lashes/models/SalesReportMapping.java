package com.lashes.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class SalesReportMapping {

    @NotNull(message = "from date cannot be empty")
    @NotEmpty(message = "cannot be empty")
    public String fromDate;

    @NotNull(message = "to date cannot be empty")
    @NotEmpty(message = "cannot be empty")
    public String toDate;
    public String salesType;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }
}
