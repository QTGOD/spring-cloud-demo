package com.zt.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

@SpringBootTest
public class OrderTest {

    @Autowired
    DiscoveryClient discoveryClient;

    @Test
    void OrderPortTest() {
        List<String> services = discoveryClient.getServices();
        services.forEach(service -> {
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            instances.forEach(instance -> {
                System.out.println("PORT:" + instance.getPort());
                System.out.println("URI:" + instance.getUri());
            });
        });
    }
}
