package com.example.eshop.admin.dao;

import com.example.eshop.admin.domain.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserDao extends JpaRepository<SystemUser, Integer> {
    SystemUser findByUsername(String username);

    SystemUser findByUsernameAndPassword(String username, String password);
}