package com.example.eshop.admin.service.impl;

import com.example.eshop.admin.dao.GoodsIntroductionDao;
import com.example.eshop.admin.domain.GoodsIntroduction;
import com.example.eshop.admin.service.GoodsIntroductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GoodsIntroductionServiceImpl implements GoodsIntroductionService {
    @Autowired
    private GoodsIntroductionDao goodsIntroductionDao;

    public GoodsIntroduction findById(Integer id) {
        Optional<GoodsIntroduction> item = goodsIntroductionDao.findById(id);
        return item.get();
    }

    public GoodsIntroduction save(GoodsIntroduction goodsIntroduction) {
        return goodsIntroductionDao.save(goodsIntroduction);
    }
}
