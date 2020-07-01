package com.example.eshop.admin.service.impl;

import com.example.eshop.admin.dao.GoodsDao;
import com.example.eshop.admin.domain.Goods;
import com.example.eshop.admin.service.GoodsService;
import com.example.eshop.admin.util.ReflectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    public Page<Goods> findAll(Pageable pageable) {
        return goodsDao.findAll(pageable);
    }

    public Goods findById(Integer id) {
        Optional<Goods> item = goodsDao.findById(id);
        return item.get();
    }

    public Goods save(Goods goods) {
        if (goods.getId() != null) {
            Goods origin = findById(goods.getId());
            ReflectionUtil.copyNotNullProperties(goods, origin);
            return goodsDao.save(origin);
        }
        return goodsDao.save(goods);
    }
}
