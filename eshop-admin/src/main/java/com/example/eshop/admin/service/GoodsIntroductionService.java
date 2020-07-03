package com.example.eshop.admin.service;

import com.example.eshop.admin.domain.GoodsIntroduction;

public interface GoodsIntroductionService {
    GoodsIntroduction findById(Integer id);

    GoodsIntroduction save(GoodsIntroduction goodsIntroduction);
}
