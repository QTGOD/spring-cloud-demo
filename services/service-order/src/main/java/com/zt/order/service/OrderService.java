package com.zt.order.service;

import com.zt.model.order.pojo.Order;

public interface OrderService {

    Order createOrder(Long userId, Long productId);

}
