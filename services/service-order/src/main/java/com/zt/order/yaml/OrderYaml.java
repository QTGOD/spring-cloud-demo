package com.zt.order.yaml;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "order")
@Data
public class OrderYaml {

    private String timeout;
    private String autoConfirm;
    private String x;
    private String y;
    private String z;
}
