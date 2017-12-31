package com.lashes.services;

import com.lashes.dao.FinanceDao;
import com.lashes.entities.Finance;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;


@Service
public class FinanceServicesImpl implements FinanceServices {

    @Autowired
    private FinanceDao financeDao;

    @Override
    public void targetActivate(Finance finance) {
        financeDao.targetActivated(finance);
    }

    @Override
    public Double profitAmount() {
        return financeDao.profitAmount();
    }

    @Override
    public Double profitPercent() {
        return financeDao.profitPercent();
    }

    @Override
    public Double salesTargetStartingNow() {
        return financeDao.salesTargetStartingNow();
    }

    @Override
    public Double targetForToday(){
        Double salesYesterday = financeDao.salesYesterday();

        if(salesYesterday == null){
            salesYesterday=0.0;
        }

        Finance finance = financeDao.getFinance();

        //Date createdDate = finance.getCreatedDate();
        Date targetDate = finance.getTargetDate();

        //converting date to joda date

        DateTime jNewDate = new DateTime(finance.getCreatedDate());
        DateTime jTargetDate = new DateTime(targetDate);

        int remainingDays= Days.daysBetween(jNewDate.toLocalDate(),jTargetDate.toLocalDate()).getDays();

        Double target = finance.getTargetAmount();

        if(remainingDays ==0){
            Double targetToday = salesYesterday/1;
            return targetToday;
        }

        Double amountRemaining = target-salesYesterday;

        Double targetToday = Double.valueOf(Math.round(amountRemaining/remainingDays));
        System.out.println(targetToday);
        return targetToday;

    }
}
