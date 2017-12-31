package com.lashes.dao;

import com.fasterxml.jackson.databind.node.LongNode;
import com.lashes.entities.Sales;
import com.lashes.models.SalesReportMapping;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public class ReportDaoImpl implements ReportDao {

    @PersistenceContext
    EntityManager em;

    public List<Sales> getSalesReportList(Date fromDate, Date toDate, String salesType) {
        return salesReportWithFromDateandToDate(fromDate,toDate,salesType);
    }


    private List<Sales>  salesReportWithFromDateandToDate(Date fromDate, Date toDate, String salesType){
        Query query=em.createQuery("select s from Sales s where s.salesType=:salesType and s.createdDate between :fDate and :tDate")
                .setParameter("fDate",fromDate)
                .setParameter("tDate",toDate)
                .setParameter("salesType",salesType);
        List<Sales> salesList = query.getResultList();
        return salesList;
    }


    @Override
    public List<Sales> totalSalesReport(Date fromDate, Date toDate) {
        Query query=em.createQuery("select s from Sales s where s.createdDate between :fDate and :tDate")
                .setParameter("fDate",fromDate)
                .setParameter("tDate",toDate);
        List<Sales> salesList = query.getResultList();
        return salesList;
    }

    @Override
    public Double totalSalesSum(Date fDate, Date tDate) {
        Query query = em.createQuery("select sum(totalPrice) from Sales s where s.createdDate between :fDate and :tDate")
                .setParameter("fDate",fDate)
                .setParameter("tDate",tDate);
        Double sales = (Double) query.getSingleResult();
        return sales;
    }

    public Double totalSalesSumBySalesType(Date fDate, Date tDate,String salesType) {
        Query query = em.createQuery("select sum(totalPrice) from Sales s where s.createdDate between :fDate and :tDate and s.salesType=:salesType")
                .setParameter("fDate",fDate)
                .setParameter("tDate",tDate)
                .setParameter("salesType",salesType);
        Double sales = (Double) query.getSingleResult();
        return sales;
    }
}
