package com.zt.model.order.pojo;

import com.zt.model.product.pojo.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {

    private Long id;
    private BigDecimal totalPrice;
    private Long userId;
    private String nickName;
    private String address;
    private List<Product> productList;
}
