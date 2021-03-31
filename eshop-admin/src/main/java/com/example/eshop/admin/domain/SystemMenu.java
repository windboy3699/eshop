package com.example.eshop.admin.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Table(name="system_menu")
@Entity
@DynamicInsert
@DynamicUpdate
public class SystemMenu {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer level;
    private Integer topid;
    private String path;
    private String link;
    private String icon;
    private Integer visible;
    private Integer sort;

    public Integer getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Integer getLevel() { return level; }

    public void setLevel(Integer level) { this.level = level; }

    public Integer getTopid() { return topid; }

    public void setTopid(Integer topid) { this.topid = topid; }

    public String getPath() { return path; }

    public void setPath(String path) { this.path = path; }

    public String getLink() { return link; }

    public void setLink(String link) { this.link = link; }

    public String getIcon() { return icon; }

    public void setIcon(String icon) { this.icon = icon; }

    public Integer getVisible() { return visible; }

    public void setVisible(Integer visible) { this.visible = visible; }

    public Integer getSort() { return sort; }

    public void setSort(Integer sort) { this.sort = sort; }
}
