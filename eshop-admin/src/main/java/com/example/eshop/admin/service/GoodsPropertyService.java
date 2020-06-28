package com.example.eshop.admin.service;

import com.example.eshop.admin.domain.GoodsProperty;

import java.util.List;

public interface GoodsPropertyService {
    List<GoodsProperty> findByCategoryId(Integer categoryId);
}
