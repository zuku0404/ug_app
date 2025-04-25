package com.mateusz.zuk.currency.client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public static RestClient restClient() {
        return RestClient.builder()
                .baseUrl("https://api.nbp.pl")
                .build();
    }
}