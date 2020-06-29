package com.example.eshop.admin.service.impl;

import com.example.eshop.admin.dao.GoodsCategoryDao;
import com.example.eshop.admin.domain.GoodsCategory;
import com.example.eshop.admin.service.GoodsCategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {
    @Autowired
    private GoodsCategoryDao goodsCategoryDao;

    private Map<Integer, GoodsCategory> allCategoryMap = null;

    public GoodsCategory findById(Integer id) {
        Optional<GoodsCategory> item = goodsCategoryDao.findById(id);
        return item.get();
    }

    public List<GoodsCategory> findAll() { return goodsCategoryDao.findAll(); }

    public List<GoodsCategory> findByParentId(Integer parentId) {
        return goodsCategoryDao.findByParentId(parentId);
    }

    public GoodsCategory save(GoodsCategory category) {
        return goodsCategoryDao.save(category);
    }

    public Map<Integer, GoodsCategory> getAllCategoryMap() {
        if (allCategoryMap == null) {
            List<GoodsCategory> allCategoryList = findAll();
            Map<Integer, GoodsCategory> allCategory = new HashMap<>();
            for (GoodsCategory item : allCategoryList) {
                allCategory.put(item.getId(), item);
            }
            allCategoryMap = allCategory;
        }
        return allCategoryMap;
    }

    public List<Integer> getParentsIdList(Integer parentId, List<Integer> list) {
        if (parentId == 0) {
            List<Integer> sortList = new ArrayList<>();
            for (Integer i = list.size() - 1; i >= 0; i--) {
                sortList.add(list.get(i));
            }
            return sortList;
        }
        Map<Integer, GoodsCategory> allCategory = getAllCategoryMap();
        list.add(allCategory.get(parentId).getId());
        return getParentsIdList(allCategory.get(parentId).getParentId(), list);
    }

    public String getParentsJoinName(Integer parentId) {
        List<Integer> parentIdList = getParentsIdList(parentId, new ArrayList<Integer>());
        Map<Integer, GoodsCategory> allCategory = getAllCategoryMap();
        List<String> nameList = new ArrayList<>();
        for (Integer pid : parentIdList) {
            String name = allCategory.get(pid).getName();
            nameList.add(name);
        }
        return StringUtils.join(nameList.toArray(), " > ");
    }

    public String getFullJoinName(Integer id) {
        GoodsCategory goodsCategory = findById(id);
        String parentsJoinName = getParentsJoinName(goodsCategory.getParentId());
        return parentsJoinName + " > " + goodsCategory.getName();
    }
}
