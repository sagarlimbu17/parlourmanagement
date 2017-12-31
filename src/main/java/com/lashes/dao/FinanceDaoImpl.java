package com.lashes.dao;

import com.lashes.entities.Finance;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Repository
@Transactional
public class FinanceDaoImpl implements FinanceDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void targetActivated(Finance finance) {
        if(getFinanceById()==null){
            em.persist(finance);
            return;
        }

        finance.setId(getFinanceById().getId());
        em.merge(finance);

    }

    @Override
    public Double profitAmount() {
        return getTotalSellingPrice()-getTotalCostPrice();
    }

    public Double getTotalCostPrice(){
        Query query = em.createNativeQuery("SELECT sum(totalCostPrice) FROM  Sales where createdDate=curdate()");
        Double costPrice = (Double) query.getSingleResult();
        if(costPrice==null){
            return 0.0;
        }
        return costPrice;

    }

    public Double getTotalSellingPrice(){
        Query query = em.createNativeQuery("SELECT sum(totalPrice) FROM  Sales where createdDate=curdate()");
        Double sellingPrice = (Double) query.getSingleResult();
        if(sellingPrice ==  null){
            return 0.0;
        }
        return sellingPrice;

    }

    public Finance getFinanceById(){
        Finance finance = em.find(Finance.class,1L);
        return finance;
    }

    @Override
    public Double profitPercent() {
        Double amount = getTotalSellingPrice()-getTotalCostPrice();
        System.out.println(amount);
        Double percent = (amount/getTotalCostPrice())*100;
        Double roundedPercent= Double.valueOf(Math.round(percent));
        System.out.println(roundedPercent);
        return roundedPercent;
    }

    @Override
    public Double salesTargetStartingNow() {

        //selecting initial date
        Finance finance = getFinance();

        Date d = finance.getCreatedDate();
        Date dTarget = finance.getTargetDate();

        //total sales from initial date to current date
        Query query = em.createNativeQuery("select sum(totalPrice) from Sales where createdDate between :d and curDate()")
                .setParameter("d",d);
        Double totalCurrentSales = (Double) query.getSingleResult();

        Query query1 = em.createNativeQuery("select sum(totalPrice) from Sales where createdDate=curDate()");
        Double totalSalesToday = (Double) query1.getSingleResult();

        if(totalCurrentSales==null || totalSalesToday==null){
            totalSalesToday = 0.0;
        }

        //joda initital datetime
        DateTime intdt = new DateTime(new Date());
        DateTime targetDt = new DateTime(dTarget);

        int noOfDays = Days.daysBetween(intdt.toLocalDate(),targetDt.toLocalDate()).getDays();
        System.out.println(noOfDays);

        //setting up new target for showing daily total sales target starting now
        Double target = finance.getTargetAmount();
        Double newTarget = target-totalSalesToday;

        if(totalCurrentSales == null){
            Double salesYesterday = salesYesterday();
            if(salesYesterday==null){
                salesYesterday=0.0;
            }
            Double remainingSales = newTarget- salesYesterday;
            return Double.valueOf(Math.round(remainingSales/noOfDays));
        }

        Double remainingTargetSales = newTarget-totalCurrentSales;

        return Double.valueOf(Math.round(remainingTargetSales/noOfDays));
    }

    public Finance getFinance(){
        Query qry= em.createQuery("select s from Finance s");
        Finance finance = (Finance)  qry.getSingleResult();
        return finance;
    }

    @Override
    public Double salesYesterday() {
        Finance finance = getFinance();
        Date startDate = finance.getCreatedDate();

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(startDate);
        cal2.setTime(new Date());

        boolean sameDay = cal1.get(Calendar.YEAR)== cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR)== cal2.get(Calendar.DAY_OF_YEAR);

        if(sameDay){
            Query query = em.createNativeQuery("select sum(totalPrice) from Sales s where s.createdDate <=curdate()-1");
            Double salesUptoYesterday = (Double)query.getSingleResult();
            return salesUptoYesterday;
        }
        Query query = em.createNativeQuery("select sum(totalPrice) from Sales s where s.createdDate BETWEEN :startDate and curdate()-1")
                .setParameter("startDate",startDate);
        Double salesYesterday = (Double) query.getSingleResult();
        return salesYesterday;
    }
}
