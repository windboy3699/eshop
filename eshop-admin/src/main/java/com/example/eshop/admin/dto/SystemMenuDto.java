package com.example.eshop.admin.dto;

import java.util.List;

public class SystemMenuDto {
    private Integer id;

    private String name;

    private Integer topid;

    private String link;

    private Boolean active = false;

    private List<SystemMenuDto> children;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Integer getTopid() { return topid; }

    public void setTopid(Integer topid) { this.topid = topid; }

    public String getLink() { return link; }

    public void setLink(String link) { this.link = link; }

    public Boolean getActive() { return active; }

    public void setActive(Boolean active) { this.active = active; }

    public List<SystemMenuDto> getChildren() { return children; }

    public void setChildren(List<SystemMenuDto> children) { this.children = children; }
}
