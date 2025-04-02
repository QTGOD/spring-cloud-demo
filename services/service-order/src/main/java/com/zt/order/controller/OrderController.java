package com.zt.order.controller;

import com.zt.model.order.pojo.Order;
import com.zt.order.service.OrderService;
import com.zt.order.yaml.OrderYaml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/order")
@RefreshScope
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderYaml orderYaml;

    @GetMapping("/config")
    public String getConfig(){
        return "timeout: "+orderYaml.getTimeout() +
                " - autoConfirm: "+orderYaml.getAutoConfirm() +
                " - x: "+orderYaml.getX() +
                " - y: "+orderYaml.getY() +
                " - z: "+orderYaml.getZ();
    }

    @GetMapping("/create")
    public Order getOrder(@RequestParam("userId") Long userId,
                          @RequestParam("productId") Long productId){
        return orderService.createOrder(userId,productId);
    }
}
