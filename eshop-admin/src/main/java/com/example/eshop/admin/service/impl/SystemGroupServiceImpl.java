package com.example.eshop.admin.service.impl;

import com.example.eshop.admin.dao.SystemGroupDao;
import com.example.eshop.admin.domain.SystemGroup;
import com.example.eshop.admin.service.SystemGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SystemGroupServiceImpl implements SystemGroupService {
    @Autowired
    private SystemGroupDao systemGroupDao;

    public List<SystemGroup> findAll() {
        return systemGroupDao.findAll();
    }

    public SystemGroup findById(int id) {
        Optional<SystemGroup> groupById = systemGroupDao.findById(id);
        return groupById.get();
    }
}
