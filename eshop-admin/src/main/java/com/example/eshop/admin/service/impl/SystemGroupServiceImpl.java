package com.example.eshop.admin.service.impl;

import com.example.eshop.admin.dao.SystemGroupDao;
import com.example.eshop.admin.domain.SystemGroup;
import com.example.eshop.admin.service.SystemGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<SystemGroup> findAll(Pageable pageable) {
        return systemGroupDao.findAll(pageable);
    }

    public SystemGroup findById(int id) {
        Optional<SystemGroup> groupById = systemGroupDao.findById(id);
        return groupById.get();
    }

    public SystemGroup save(SystemGroup systemGroup) { return systemGroupDao.save(systemGroup); }

    public void deleteById(int id) {
        systemGroupDao.deleteById(id);
    }
}
