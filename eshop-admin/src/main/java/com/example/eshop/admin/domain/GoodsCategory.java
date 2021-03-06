package com.example.eshop.admin.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Table(name="goods_category")
@Entity
@DynamicInsert
@DynamicUpdate
public class GoodsCategory {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer parentId;
    private String name;
    private Integer sort;

    public Integer getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Integer getParentId() { return parentId; }

    public void setParentId(Integer parentId) { this.parentId = parentId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Integer getSort() { return sort; }

    public void setSort(Integer sort) { this.sort = sort; }
}
