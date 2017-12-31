package com.lashes.services;

import com.lashes.dao.ReportDao;
import com.lashes.entities.Sales;
import com.lashes.models.SalesReportMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ReportServicesImpl implements ReportServices {

    @Autowired
    private ReportDao reportDao;

    public List<Sales> getSalesReportList(Date fromDate, Date toDate, String salestype) {
        return reportDao.getSalesReportList(fromDate,toDate,salestype);
    }

    @Override
    public List<Sales> totalSalesReport(Date fromDate, Date toDate) {
        return reportDao.totalSalesReport(fromDate,toDate);
    }

    @Override
    public Double totalSalesSum(Date fDate, Date tDate) {
        return reportDao.totalSalesSum(fDate,tDate);
    }


    public Double totalSalesSumBySalesType(Date fDate, Date tDate,String salesType) {
        return reportDao.totalSalesSumBySalesType(fDate,tDate,salesType);
    }
}
