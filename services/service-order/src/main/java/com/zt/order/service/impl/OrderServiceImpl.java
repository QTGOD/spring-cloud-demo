package com.zt.order.service.impl;

import com.alibaba.cloud.nacos.discovery.NacosDiscoveryClient;
import com.zt.model.order.pojo.Order;
import com.zt.model.product.pojo.Product;
import com.zt.order.config.OrderServiceConfig;
import com.zt.order.service.OrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderServiceConfig instance;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Resource
    private NacosDiscoveryClient nacosDiscoveryClient;

    @Override
    public Order createOrder(Long userId, Long productId) {
        Product product = getProductByIdAndLoadBalanceAnnotation(productId);
        Order order = new Order();
        order.setId(12L);
        order.setUserId(userId);
        order.setTotalPrice(product.getPrice().multiply(new BigDecimal(product.getNum())));
        order.setNickName("zt");
        order.setAddress("北京");
        // 远程调用API接口
        order.setProductList(List.of(product));
        return order;
    }
    //远程调用方法1
    private Product getRemoteProductById(Long productId) {
        List<String> services = nacosDiscoveryClient.getServices();
        List<ServiceInstance> instances = nacosDiscoveryClient.getInstances(services.get(0));
        String url = "http://" + instances.get(0).getHost() + ":" + instances.get(0).getPort() + "/product/" + productId;
        return instance.restTemplate().getForObject(url, Product.class);
    }
    // 远程调用方法2
    private Product getProductByIdAndLoadBalance(Long productId) {
        ServiceInstance inst = loadBalancerClient.choose("product-service");
        String url = "http://" + inst.getHost() + ":" + inst.getPort() + "/product/" + productId;
        log.info("调用地址：" + url);
        return instance.restTemplate().getForObject(url, Product.class);
    }

    // 远程调用方法3 注解法
    private Product getProductByIdAndLoadBalanceAnnotation(Long productId) {
        String url = "http://product-service/product/"+productId;
        // 给远程发送请求； service-product 会被动态替换
        return instance.restTemplate().getForObject(url, Product.class);
    }
}
