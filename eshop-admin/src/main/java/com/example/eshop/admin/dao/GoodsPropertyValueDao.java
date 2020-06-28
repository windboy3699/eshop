package com.example.eshop.admin.dao;

import com.example.eshop.admin.domain.GoodsPropertyValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsPropertyValueDao extends JpaRepository<GoodsPropertyValue, Integer> {
    List<GoodsPropertyValue> findByPropertyId(Integer propertyId);
}
