package com.example.crypto.controller;

import com.example.crypto.model.ExchangeRate;
import com.example.crypto.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/exchange")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @GetMapping("bitcoin/latest")
    public ExchangeRate getBitcoinLatestRate() {
        return exchangeRateRepository.getBitcoinLatestRate();
    }

    @GetMapping("bitcoin/historical/{fromDate}/{toDate}")
    public List<ExchangeRate> getBitcoinHistoricalRates(@PathVariable Date fromDate, @PathVariable Date toDate) {
        return exchangeRateRepository.getBitcoinHistoricalRates(fromDate, toDate);
    }
}
