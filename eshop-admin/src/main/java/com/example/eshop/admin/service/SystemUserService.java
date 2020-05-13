package com.example.eshop.admin.service;

import com.example.eshop.admin.domain.SystemUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SystemUserService {
    Page<SystemUser> findAll(Pageable page);

    SystemUser findById(int id);

    SystemUser findByUsername(String username);

    SystemUser findByUsernameAndPassword(String username, String password);

    Boolean add(SystemUser systemUser);

    void deleteById(int id);
}
