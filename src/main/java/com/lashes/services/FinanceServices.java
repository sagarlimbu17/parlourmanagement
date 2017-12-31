package com.lashes.services;

import com.lashes.entities.Finance;

import java.text.ParseException;


public interface FinanceServices {

    void targetActivate(Finance finance);
    Double profitAmount();
    Double profitPercent();
    Double salesTargetStartingNow();
    Double targetForToday();
}
