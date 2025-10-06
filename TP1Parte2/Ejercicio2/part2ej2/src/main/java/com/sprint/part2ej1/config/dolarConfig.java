package com.sprint.part2ej1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class dolarConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
