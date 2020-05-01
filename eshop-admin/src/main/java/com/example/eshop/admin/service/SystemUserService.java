package com.example.eshop.admin.service;

import com.example.eshop.admin.domain.SystemUser;

import java.util.List;

public interface SystemUserService {
    List<SystemUser> findAll();

    SystemUser findById(int id);

    SystemUser findByUsername(String username);

    SystemUser findByUsernameAndPassword(String username, String password);

    Boolean add(SystemUser systemUser);

    void deleteById(int id);
}
