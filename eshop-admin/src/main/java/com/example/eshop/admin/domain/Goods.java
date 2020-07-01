package com.example.eshop.admin.domain;

import com.example.eshop.admin.util.DbJoinStringConverterUtil;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Table(name="goods")
@Entity
@DynamicInsert
@DynamicUpdate
public class Goods {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String clusterCode;
    private Integer categoryId;
    @Convert(converter= DbJoinStringConverterUtil.class)
    private List<Integer> properties;
    private Double price;
    private Integer stock;
    private String image;
    private String created;

    public Integer getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getClusterCode() { return clusterCode; }

    public void setClusterCode(String clusterCode) { this.clusterCode = clusterCode; }

    public Integer getCategoryId() { return categoryId; }

    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public List<Integer> getProperties() { return properties; }

    public void setProperties(List<Integer> properties) { this.properties = properties; }

    public Double getPrice() { return price; }

    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return stock; }

    public void setStock(Integer stock) { this.stock = stock; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public String getCreated() { return created; }

    public void setCreated(String created) { this.created = created; }
}
