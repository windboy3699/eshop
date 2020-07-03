package com.example.eshop.admin.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Table(name="goods_introduction")
@Entity
@DynamicInsert
@DynamicUpdate
public class GoodsIntroduction {
    @Id
    private Integer goodsId;

    private String introduction;

    public Integer getId() { return goodsId; }

    public void setId(Integer id) { this.goodsId = id; }

    public String getIntroduction() { return introduction; }

    public void setIntroduction(String introduction) { this.introduction = introduction; }
}
