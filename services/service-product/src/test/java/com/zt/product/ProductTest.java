package com.zt.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

@SpringBootTest
public class ProductTest {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Test
    void loadBalanceTest() {
        ServiceInstance choose = loadBalancerClient.choose("product-service");
        System.out.println(choose.getHost() + ":" + choose.getPort());
    }

}
