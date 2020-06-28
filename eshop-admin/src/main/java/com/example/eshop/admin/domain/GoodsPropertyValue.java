package com.example.eshop.admin.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Table(name="goods_property_value")
@Entity
@DynamicInsert
@DynamicUpdate
public class GoodsPropertyValue {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer propertyId;
    private String name;

    public Integer getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Integer getPropertyId() { return propertyId; }

    public void setPropertyId(Integer propertyId) { this.propertyId = propertyId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
