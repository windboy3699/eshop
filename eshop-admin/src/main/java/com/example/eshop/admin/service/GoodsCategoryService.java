package com.example.eshop.admin.service;

import com.example.eshop.admin.domain.GoodsCategory;

import java.util.List;

public interface GoodsCategoryService {
    GoodsCategory findById(Integer id);

    List<GoodsCategory> findByParentId(Integer parentId);

    GoodsCategory save(GoodsCategory category);
}
