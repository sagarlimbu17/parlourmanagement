package com.lashes.dao;

import com.lashes.entities.Finance;

import java.text.ParseException;

public interface FinanceDao {

    void targetActivated(Finance finance);
    Double profitAmount();
    Double profitPercent();
    Double salesTargetStartingNow();
    Double salesYesterday();
    Finance getFinance();
}
