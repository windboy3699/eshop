package com.example.eshop.admin.dto;

import java.util.List;

public class SystemMenuDto {
    private Integer id;

    private Integer topid;

    private String name;

    private String link;

    private String icon;

    private List<SystemMenuDto> children;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Integer getTopid() { return topid; }

    public void setTopid(Integer topid) { this.topid = topid; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getLink() { return link; }

    public void setLink(String link) { this.link = link; }

    public String getIcon() { return icon; }

    public void setIcon(String icon) { this.icon = icon; }

    public List<SystemMenuDto> getChildren() { return children; }

    public void setChildren(List<SystemMenuDto> children) { this.children = children; }
}
