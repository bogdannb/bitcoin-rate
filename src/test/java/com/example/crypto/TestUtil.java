package com.example.crypto;

import com.example.crypto.model.Currency;
import com.example.crypto.model.ExchangeRate;

import java.util.Calendar;
import java.util.Date;

public class TestUtil {

    public static ExchangeRate getExchangeRate(double rate) {
        return getExchangeRate(rate, getCurrentDate());
    }

    public static ExchangeRate getExchangeRate(double rate, Date date) {
        return ExchangeRate.builder()
                .rate(rate)
                .fromCurrency(Currency.BITCOIN)
                .toCurrency(Currency.USD)
                .exchangeDate(date)
                .build();
    }

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }
}
