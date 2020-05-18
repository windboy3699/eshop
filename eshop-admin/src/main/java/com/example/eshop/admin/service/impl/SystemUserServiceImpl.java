package com.example.eshop.admin.service.impl;

import com.example.eshop.admin.dao.SystemUserDao;
import com.example.eshop.admin.domain.SystemUser;
import com.example.eshop.admin.service.SystemUserService;
import com.example.eshop.admin.util.ReflectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class SystemUserServiceImpl implements SystemUserService {
    @Autowired
    SystemUserDao systemUserDao;

    public Page<SystemUser> findAll(Pageable pageable) {
        return systemUserDao.findAll(pageable);
    }

    public SystemUser findById(int id) {
        Optional<SystemUser> userById = systemUserDao.findById(id);
        return userById.get();
    }

    public SystemUser findByUsername(String username) {
        return systemUserDao.findByUsername(username);
    }

    public SystemUser findByUsernameAndPassword(String username, String password) {
        return systemUserDao.findByUsernameAndPassword(username, password);
    }

    public SystemUser save(SystemUser systemUser) {
        if (systemUser.getId() != null) {
            SystemUser originSystemUser = findById(systemUser.getId());
            ReflectionUtil.copyNotNullProperties(systemUser, originSystemUser);
            return systemUserDao.save(originSystemUser);
        }
        return systemUserDao.save(systemUser);
    }

    public void deleteById(int id) {
        systemUserDao.deleteById(id);
    }
}
