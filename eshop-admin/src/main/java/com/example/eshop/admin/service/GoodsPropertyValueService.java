package com.example.eshop.admin.service;

import com.example.eshop.admin.domain.GoodsPropertyValue;

import java.util.List;
import java.util.Map;

public interface GoodsPropertyValueService {
    GoodsPropertyValue findById(Integer id);

    List<GoodsPropertyValue> findAll();

    List<GoodsPropertyValue> findByPropertyId(Integer propertyId);

    GoodsPropertyValue save(GoodsPropertyValue goodsPropertyValue);

    Map<Integer, String> getAllNameMap();
}
