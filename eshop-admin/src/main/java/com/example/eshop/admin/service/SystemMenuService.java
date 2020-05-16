package com.example.eshop.admin.service;

import com.example.eshop.admin.domain.SystemMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SystemMenuService {
    List<SystemMenu> findAll();

    SystemMenu findById(Integer id);

    Page<SystemMenu> findByTopid(Integer topid, Pageable page);

    List<SystemMenu> findByLevel(Integer level);

    List<SystemMenu> findByIdIn(List<Integer> ids);
}
