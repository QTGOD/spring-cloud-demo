package com.zt.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OrderServiceConfig {

    @Bean
    @LoadBalanced //远程调用方法3
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
