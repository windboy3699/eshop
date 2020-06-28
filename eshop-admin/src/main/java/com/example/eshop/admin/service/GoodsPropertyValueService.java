package com.example.eshop.admin.service;

import com.example.eshop.admin.domain.GoodsPropertyValue;

import java.util.List;

public interface GoodsPropertyValueService {
    GoodsPropertyValue findById(Integer id);

    List<GoodsPropertyValue> findByPropertyId(Integer propertyId);

    GoodsPropertyValue save(GoodsPropertyValue goodsPropertyValue);
}
