package com.lashes.dao;

import com.lashes.entities.Sales;
import com.lashes.models.SalesReportMapping;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ReportDao {

    List<Sales> getSalesReportList(Date fromDate, Date toDate, String salesType);
    List<Sales> totalSalesReport(Date fromDate, Date toDate);
    Double totalSalesSum(Date fDate, Date tDate);
    Double totalSalesSumBySalesType(Date fDate, Date tDate, String salesType);
}
