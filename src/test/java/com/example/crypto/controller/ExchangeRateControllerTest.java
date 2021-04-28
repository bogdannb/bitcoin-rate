package com.example.crypto.controller;

import com.example.crypto.model.ExchangeRate;
import com.example.crypto.repository.ExchangeRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.example.crypto.TestUtil.getCurrentDate;
import static com.example.crypto.TestUtil.getExchangeRate;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ExchangeRateControllerTest {

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @InjectMocks
    private ExchangeRateController exchangeRateController;

    @Test
    void getBitcoinLatestRate() {
        final ExchangeRate exchangeRate = getExchangeRate(58);
        Mockito.when(exchangeRateRepository.getBitcoinLatestRate()).thenReturn(exchangeRate);

        ExchangeRate bitcoinExchangeRate = exchangeRateController.getBitcoinLatestRate();

        assertEquals(exchangeRate, bitcoinExchangeRate);
    }

    @Test
    void getBitcoinHistoricalRates() {
        List<ExchangeRate> exchangeRates = Arrays.asList(getExchangeRate(58), getExchangeRate(60));
        Mockito.when(exchangeRateRepository.getBitcoinHistoricalRates(any(Date.class), any(Date.class)))
                .thenReturn(exchangeRates);

        List<ExchangeRate> bitcoinExchangeRates = exchangeRateController.getBitcoinHistoricalRates(getCurrentDate(),
                getCurrentDate());

        assertEquals(exchangeRates, bitcoinExchangeRates);
    }


}