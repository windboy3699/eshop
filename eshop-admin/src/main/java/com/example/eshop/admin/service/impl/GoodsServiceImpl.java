package com.example.eshop.admin.service.impl;

import com.example.eshop.admin.dao.GoodsDao;
import com.example.eshop.admin.domain.Goods;
import com.example.eshop.admin.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    public Page<Goods> findAll(Pageable pageable) {
        return goodsDao.findAll(pageable);
    }
}
