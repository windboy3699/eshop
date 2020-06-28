package com.example.eshop.admin.dao;

import com.example.eshop.admin.domain.GoodsProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsPropertyDao extends JpaRepository<GoodsProperty, Integer> {
    List<GoodsProperty> findByCategoryId(Integer categoryId);
}
