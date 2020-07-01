package com.example.eshop.admin.service.impl;

import com.example.eshop.admin.dao.GoodsPropertyValueDao;
import com.example.eshop.admin.domain.GoodsPropertyValue;
import com.example.eshop.admin.service.GoodsPropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GoodsPropertyValueServiceImpl implements GoodsPropertyValueService {
    @Autowired
    private GoodsPropertyValueDao goodsPropertyValueDao;

    public GoodsPropertyValue findById(Integer id) {
        Optional<GoodsPropertyValue> item = goodsPropertyValueDao.findById(id);
        return item.get();
    }

    public List<GoodsPropertyValue> findAll() {
        return goodsPropertyValueDao.findAll();
    }

    public List<GoodsPropertyValue> findByPropertyId(Integer propertyId) {
        return goodsPropertyValueDao.findByPropertyId(propertyId);
    }

    public GoodsPropertyValue save(GoodsPropertyValue goodsPropertyValue) {
        return goodsPropertyValueDao.save(goodsPropertyValue);
    }

    public Map<Integer, String> getAllNameMap() {
        Map<Integer, String> map = new HashMap<>();
        List<GoodsPropertyValue> goodsPropertyValueList = findAll();
        for (GoodsPropertyValue item : goodsPropertyValueList) {
            map.put(item.getId(), item.getName());
        }
        return map;
    }
}
