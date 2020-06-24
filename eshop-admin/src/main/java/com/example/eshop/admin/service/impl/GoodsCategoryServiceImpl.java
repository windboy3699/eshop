package com.example.eshop.admin.service.impl;

import com.example.eshop.admin.dao.GoodsCategoryDao;
import com.example.eshop.admin.domain.GoodsCategory;
import com.example.eshop.admin.service.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {
    @Autowired
    private GoodsCategoryDao goodsCategoryDao;

    public GoodsCategory findById(Integer id) {
        Optional<GoodsCategory> item = goodsCategoryDao.findById(id);
        return item.get();
    }

    public List<GoodsCategory> findByParentId(Integer parentId) {
        return goodsCategoryDao.findByParentId(parentId);
    }

    public GoodsCategory save(GoodsCategory category) {
        return goodsCategoryDao.save(category);
    }
}
