package com.example.eshop.admin.service;

import com.example.eshop.admin.dto.SystemMenuDto;

import java.util.List;

public interface SystemLeftMenuService {
    List<SystemMenuDto> get(int groupId);
}
