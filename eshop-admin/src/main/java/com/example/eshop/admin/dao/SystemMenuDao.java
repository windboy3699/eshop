package com.example.eshop.admin.dao;

import com.example.eshop.admin.domain.SystemMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemMenuDao extends JpaRepository<SystemMenu, Integer> {
    @Query(value = "select * from system_menu where level = ?1 and visible = 1 order by sort,id asc", nativeQuery = true)
    List<SystemMenu> findByLevel(Integer level);

    @Query(value = "select * from system_menu where visible = 1 order by sort,id asc", nativeQuery = true)
    List<SystemMenu> findAll();

    List<SystemMenu> findByIdIn(List<Integer> ids);
}
