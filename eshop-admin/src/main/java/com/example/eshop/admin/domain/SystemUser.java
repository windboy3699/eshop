package com.example.eshop.admin.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Table(name="system_user")
@Entity
@DynamicInsert
@DynamicUpdate
public class SystemUser {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String username;
    private String password;
    @NotNull
    private Integer groupId;
    @NotBlank
    private String realname;
    private String addUser;
    private String lastLogin;
    private String updateTime;
    private String createTime;

    public Integer getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public Integer getGroupId() { return groupId; }

    public void setGroupId(Integer groupId) { this.groupId = groupId; }

    public String getRealname() { return realname; }

    public void setRealname(String realname) { this.realname = realname; }

    public String getAddUser() {return addUser;}

    public void setAddUser(String addUser) {this.addUser = addUser;}

    public String getLastLogin() {return lastLogin;}

    public void setLastLogin(String lastLogin) {this.lastLogin = lastLogin;}

    public String getUpdateTime() {return updateTime;}

    public void setUpdateTime(String updateTime) {this.updateTime = updateTime;}

    public String getCreateTime() {return createTime;}

    public void setCreateTime(String createTime) {this.createTime = createTime;}
}
