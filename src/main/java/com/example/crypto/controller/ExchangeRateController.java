package com.example.crypto.controller;

import com.example.crypto.model.ExchangeRate;
import com.example.crypto.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("bitcoin/historical")
    public List<ExchangeRate> getBitcoinHistoricalRates(
            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date toDate) {
        return exchangeRateRepository.getBitcoinHistoricalRates(fromDate, toDate);
    }
}
