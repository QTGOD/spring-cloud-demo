package com.zt.product.service.impl;

import com.zt.model.product.pojo.Product;
import com.zt.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public Product getProductById(Long productId) {
        Product product = new Product();
        product.setProductName("Apple 16 Pro Max 1T");
        product.setId(productId);
        product.setNum(2);
        product.setPrice(new BigDecimal(999));
        return product;
    }
}
