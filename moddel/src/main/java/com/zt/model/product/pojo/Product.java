package com.zt.model.product.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {

    private Long id;
    private BigDecimal price;
    private String productName;
    private int num;
}
