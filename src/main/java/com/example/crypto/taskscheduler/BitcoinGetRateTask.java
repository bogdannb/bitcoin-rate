package com.example.crypto.taskscheduler;

import com.example.crypto.model.CoindeskResponse;
import com.example.crypto.model.Currency;
import com.example.crypto.model.ExchangeRate;
import com.example.crypto.repository.ExchangeRateRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@AllArgsConstructor
class BitcoinGetRateTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinGetRateTask.class);
    private final WebClient webClient;
    private final ExchangeRateRepository exchangeRateRepository;

    @Override
    public void run() {
        logger.info("Getting the bitcoin to USD rate");
        WebClient.ResponseSpec responseSpec = webClient.get().retrieve();
        Flux<CoindeskResponse> response = responseSpec.bodyToFlux(CoindeskResponse.class);
        response.subscribe(coindeskResponse -> {
            logger.info("Last know rate is " + coindeskResponse.getBitcoinToUsdRate() + " at " + coindeskResponse.getExchangeDateUTC());
            ExchangeRate exchangeRate = ExchangeRate.builder()
                    .fromCurrency(Currency.BITCOIN)
                    .toCurrency(Currency.USD)
                    .rate(coindeskResponse.getBitcoinToUsdRate())
                    .exchangeDate(coindeskResponse.getExchangeDateUTC())
                    .build();
            exchangeRateRepository.save(exchangeRate);
        });
    }
}