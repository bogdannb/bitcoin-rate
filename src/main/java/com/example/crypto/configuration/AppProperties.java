package com.example.crypto.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppProperties {

    @Value("${bitcoin.polling.miliseconds}")
    private long bitcoinPollingIntervalMiliseconds;


    @Value("${bitcoin.polling.threads}")
    private int bitcoinPollingThreads;

    @Value("${bitcoin.api.url}")
    private String bitcoinApiUrl;
}
