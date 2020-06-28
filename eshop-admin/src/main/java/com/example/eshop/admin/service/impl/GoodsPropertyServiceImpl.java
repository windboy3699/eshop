package com.example.eshop.admin.service.impl;

import com.example.eshop.admin.dao.GoodsPropertyDao;
import com.example.eshop.admin.domain.GoodsProperty;
import com.example.eshop.admin.service.GoodsPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsPropertyServiceImpl implements GoodsPropertyService {
    @Autowired
    private GoodsPropertyDao goodsPropertyDao;

    public List<GoodsProperty> findByCategoryId(Integer categoryId) {
        return goodsPropertyDao.findByCategoryId(categoryId);
    }
}
