package com.example.eshop.admin.service.impl;

import com.example.eshop.admin.dao.SystemMenuDao;
import com.example.eshop.admin.domain.SystemMenu;
import com.example.eshop.admin.service.SystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemMenuServiceImpl implements SystemMenuService {
    @Autowired
    private SystemMenuDao systemMenuDao;

    public List<SystemMenu> findAll() { return systemMenuDao.findAll(); }

    public List<SystemMenu> findByLevel(Integer level) {
        return systemMenuDao.findByLevel(level);
    }

    public List<SystemMenu> findByIdIn(List<Integer> ids) { return systemMenuDao.findByIdIn(ids); }
}
