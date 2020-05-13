package com.example.eshop.admin.dao;

import com.example.eshop.admin.domain.SystemUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserDao extends JpaRepository<SystemUser, Integer> {
    Page<SystemUser> findAll(Pageable pageable);

    SystemUser findByUsername(String username);

    SystemUser findByUsernameAndPassword(String username, String password);
}