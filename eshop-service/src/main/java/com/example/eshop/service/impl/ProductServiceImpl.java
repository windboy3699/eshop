package com.example.eshop.service.impl;

import com.example.eshop.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    public String getProductInfo(Integer id) {
        return "id:" + id + ",product-name:Apple";
    }
}
