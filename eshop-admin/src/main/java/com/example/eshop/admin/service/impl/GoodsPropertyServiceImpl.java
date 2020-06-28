package com.example.eshop.admin.service.impl;

import com.example.eshop.admin.dao.GoodsPropertyDao;
import com.example.eshop.admin.domain.GoodsProperty;
import com.example.eshop.admin.service.GoodsPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoodsPropertyServiceImpl implements GoodsPropertyService {
    @Autowired
    private GoodsPropertyDao goodsPropertyDao;

    public GoodsProperty findById(Integer id) {
        Optional<GoodsProperty> item = goodsPropertyDao.findById(id);
        return item.get();
    }

    public List<GoodsProperty> findByCategoryId(Integer categoryId) {
        return goodsPropertyDao.findByCategoryId(categoryId);
    }

    public GoodsProperty save(GoodsProperty property) {
        return goodsPropertyDao.save(property);
    }
}
