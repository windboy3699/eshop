package com.example.eshop.admin.service;

import com.example.eshop.admin.domain.SystemGroup;

import java.util.List;

public interface SystemGroupService {
    List<SystemGroup> findAll();

    SystemGroup findById(int id);
}
