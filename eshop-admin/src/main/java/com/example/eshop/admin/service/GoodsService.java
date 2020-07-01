package com.example.eshop.admin.service;

import com.example.eshop.admin.domain.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoodsService {
    Page<Goods> findAll(Pageable pageable);

    Goods findById(Integer id);
}
