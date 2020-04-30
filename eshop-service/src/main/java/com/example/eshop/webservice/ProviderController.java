package com.example.eshop.webservice;

import com.example.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {
    @Autowired
    private ProductService productService;

    @RequestMapping("/service/product")
    public String getProduct(@RequestParam Integer id) {
        return productService.getProductInfo(id);
    }
}
