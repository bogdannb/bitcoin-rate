package com.example.crypto.taskscheduler;

import com.example.crypto.configuration.AppProperties;
import com.example.crypto.repository.ExchangeRateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;

@Component
public class BitcoinThreadPoolTaskScheduler {

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Bean
    @Scope("singleton")
    public WebClient webClient() {
        MediaType javascriptMediaType = new MediaType("application", "javascript");
        ExchangeStrategies strategies = ExchangeStrategies
                .builder()
                .codecs(clientDefaultCodecsConfigurer -> {
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(new ObjectMapper(), javascriptMediaType));
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(new ObjectMapper(), javascriptMediaType));
                }).build();
        return WebClient.builder()
                .exchangeStrategies(strategies)
                .baseUrl(appProperties.getBitcoinApiUrl())
                .build();
    }

    @PostConstruct
    public void scheduleRunnableWithCronTrigger() {
        taskScheduler.scheduleWithFixedDelay(
                new BitcoinGetRateTask(webClient(), exchangeRateRepository),
                appProperties.getBitcoinPollingIntervalMiliseconds());
    }

}

