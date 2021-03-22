package com.example.eshop.admin.vo;

public class AccountVo {
    private Integer systemUserId;
    private String systemUsername;
    private Integer systemGroupId;
    private String systemGroupName;
    private String systemRealname;

    public Integer getSystemUserId() {
        return systemUserId;
    }

    public void setSystemUserId(Integer systemUserId) {
        this.systemUserId = systemUserId;
    }

    public String getSystemUsername() {
        return systemUsername;
    }

    public void setSystemUsername(String systemUsername) {
        this.systemUsername = systemUsername;
    }

    public Integer getSystemGroupId() {
        return systemGroupId;
    }

    public void setSystemGroupId(Integer systemGroupId) {
        this.systemGroupId = systemGroupId;
    }

    public String getSystemGroupName() {
        return systemGroupName;
    }

    public void setSystemGroupName(String systemGroupName) {
        this.systemGroupName = systemGroupName;
    }

    public String getSystemRealname() {
        return systemRealname;
    }

    public void setSystemRealname(String systemRealname) {
        this.systemRealname = systemRealname;
    }
}
