package com.example.eshop.admin.service;

import com.example.eshop.admin.domain.GoodsCategory;

import java.util.List;
import java.util.Map;

public interface GoodsCategoryService {
    GoodsCategory findById(Integer id);

    List<GoodsCategory> findAll();

    List<GoodsCategory> findByParentId(Integer parentId);

    GoodsCategory save(GoodsCategory category);

    Map<Integer, GoodsCategory> getAllCategoryMap();

    List<Integer> getParentsIdList(Integer parentId, List<Integer> list);

    String getParentsJoinName(Integer parentId);

    String getFullJoinName(Integer id);
}
