package com.example.crypto.taskscheduler;

import com.example.crypto.model.CoindeskResponse;
import com.example.crypto.model.Currency;
import com.example.crypto.model.ExchangeRate;
import com.example.crypto.repository.ExchangeRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Calendar;

import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
class BitcoinGetRateTaskTest {

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @Mock
    private WebClient webClient;
    @Mock
    private RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;

    private final CoindeskResponse coindeskResponse = CoindeskResponse.builder()
            .bitcoinToUsdRate(60)
            .exchangeDateUTC(Calendar.getInstance().getTime())
            .build();

    private final Flux<CoindeskResponse> responseFlux = Flux.just(coindeskResponse);

    private final ExchangeRate expectedExchangeRate = ExchangeRate.builder()
            .fromCurrency(Currency.BITCOIN)
            .toCurrency(Currency.USD)
            .rate(coindeskResponse.getBitcoinToUsdRate())
            .exchangeDate(coindeskResponse.getExchangeDateUTC())
            .build();

    @InjectMocks
    private BitcoinGetRateTask bitcoinGetRateTask;

    @Test
    void run() {
        Mockito.when(webClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.bodyToFlux(CoindeskResponse.class)).thenReturn(responseFlux);

        bitcoinGetRateTask.run();

        StepVerifier.create(responseFlux)
                .expectNext(coindeskResponse)
                .expectComplete()
                .verify();

        Mockito.verify(exchangeRateRepository, times(1)).save(expectedExchangeRate);
    }
}