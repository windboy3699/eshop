package com.example.eshop.admin.service;

import com.example.eshop.admin.domain.GoodsProperty;

import java.util.List;

public interface GoodsPropertyService {
    GoodsProperty findById(Integer id);

    List<GoodsProperty> findByCategoryId(Integer categoryId);

    GoodsProperty save(GoodsProperty property);
}
