package com.example.eshop.admin.dao;

import com.example.eshop.admin.domain.GoodsIntroduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsIntroductionDao extends JpaRepository<GoodsIntroduction, Integer> {
}
