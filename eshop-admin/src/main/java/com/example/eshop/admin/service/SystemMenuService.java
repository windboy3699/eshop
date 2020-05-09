package com.example.eshop.admin.service;

import com.example.eshop.admin.domain.SystemMenu;

import java.util.List;

public interface SystemMenuService {
    List<SystemMenu> findAll();

    List<SystemMenu> findByLevel(Integer level);

    List<SystemMenu> findByIdIn(List<Integer> ids);
}
