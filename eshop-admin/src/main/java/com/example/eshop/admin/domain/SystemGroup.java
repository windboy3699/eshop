package com.example.eshop.admin.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Table(name="system_group")
@Entity
@DynamicInsert
@DynamicUpdate
public class SystemGroup {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String menus;

    public Integer getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getMenus() { return menus; }

    public void setMenus(String menus) { this.menus = menus; }
}
