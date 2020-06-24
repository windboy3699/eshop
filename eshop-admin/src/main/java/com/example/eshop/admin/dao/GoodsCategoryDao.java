package com.example.eshop.admin.dao;

import com.example.eshop.admin.domain.GoodsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsCategoryDao extends JpaRepository<GoodsCategory, Integer> {
    List<GoodsCategory> findByParentId(Integer parentId);
}
