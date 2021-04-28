package com.example.crypto.controller;

import com.example.crypto.model.ExchangeRate;
import com.example.crypto.repository.ExchangeRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.example.crypto.TestUtil.getExchangeRate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ExchangeRateController.class)
public class ExchangeRateControllerIntegrationTest {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Autowired
    private WebTestClient web;

    @MockBean
    private ExchangeRateRepository exchangeRateRepository;

    @Test
    void getBitcoinLatestRate() throws ParseException {
        Date date = dateFormat.parse("28/04/2021 00:00:00");
        when(exchangeRateRepository.getBitcoinLatestRate()).thenReturn(getExchangeRate(58, date));


        web.get().uri("/exchange/bitcoin/latest")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody(String.class)
                .isEqualTo("{\"fromCurrency\":\"BITCOIN\",\"toCurrency\":\"USD\",\"exchangeDate\":\"2021-04-28T00:00:00.000+00:00\",\"rate\":58.0}");
    }

    @Test
    void getBitcoinHistoricalRates() throws ParseException {
        Date date1 = dateFormat.parse("27/04/2021 00:00:00");
        Date date2 = dateFormat.parse("27/04/2021 01:00:00");
        List<ExchangeRate> exchangeRates = Arrays.asList(getExchangeRate(58, date1), getExchangeRate(60, date2));
        when(exchangeRateRepository.getBitcoinHistoricalRates(any(Date.class), any(Date.class)))
                .thenReturn(exchangeRates);


        web.get().uri("/exchange/bitcoin/historical?fromDate=2021-04-27T00:00:000&toDate=2021-04-27T01:00:00")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody(String.class)
                .isEqualTo("[" +
                        "{\"fromCurrency\":\"BITCOIN\",\"toCurrency\":\"USD\",\"exchangeDate\":\"2021-04-27T00:00:00.000+00:00\",\"rate\":58.0}," +
                        "{\"fromCurrency\":\"BITCOIN\",\"toCurrency\":\"USD\",\"exchangeDate\":\"2021-04-27T01:00:00.000+00:00\",\"rate\":60.0}]");
    }
}
