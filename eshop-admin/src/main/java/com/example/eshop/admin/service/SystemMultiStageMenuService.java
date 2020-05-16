package com.example.eshop.admin.service;

import com.example.eshop.admin.dto.SystemMenuDto;

import java.util.List;

public interface SystemMultiStageMenuService {
    List<SystemMenuDto> getAll(int groupId);

    List<SystemMenuDto> getByGroupId(int groupId);
}
