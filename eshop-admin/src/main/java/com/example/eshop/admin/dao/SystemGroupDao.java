package com.example.eshop.admin.dao;

import com.example.eshop.admin.domain.SystemGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemGroupDao extends JpaRepository<SystemGroup, Integer> {
}
