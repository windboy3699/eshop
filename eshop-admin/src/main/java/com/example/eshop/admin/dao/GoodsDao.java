package com.example.eshop.admin.dao;

import com.example.eshop.admin.domain.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsDao extends JpaRepository<Goods, Integer> {
    Page<Goods> findAll(Pageable pageable);
}
