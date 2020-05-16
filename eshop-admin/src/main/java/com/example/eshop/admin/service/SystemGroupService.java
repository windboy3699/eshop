package com.example.eshop.admin.service;

import com.example.eshop.admin.domain.SystemGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SystemGroupService {
    List<SystemGroup> findAll();

    Page<SystemGroup> findAll(Pageable page);

    SystemGroup findById(int id);

    SystemGroup save(SystemGroup systemGroup);

    void deleteById(int id);
}
