package com.example.eshop.controller;

import com.example.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {
    @Autowired
    private ProductService productService;

    @RequestMapping("product")
    public String getProduct() {
        return productService.getProductInfo();
    }
}
