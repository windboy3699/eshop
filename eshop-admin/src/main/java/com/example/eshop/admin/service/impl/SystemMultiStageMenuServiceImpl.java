package com.example.eshop.admin.service.impl;

import com.example.eshop.admin.domain.SystemGroup;
import com.example.eshop.admin.domain.SystemMenu;
import com.example.eshop.admin.dto.SystemMenuDto;
import com.example.eshop.admin.service.SystemGroupService;
import com.example.eshop.admin.service.SystemMultiStageMenuService;
import com.example.eshop.admin.service.SystemMenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SystemMultiStageMenuServiceImpl implements SystemMultiStageMenuService {
    @Autowired
    private SystemMenuService systemMenuService;

    @Autowired
    private SystemGroupService systemGroupService;

    public List<SystemMenuDto> getAll(int groupId) {
        SystemGroup group = null;
        List<Integer> idList = new ArrayList<>();
        if (groupId > 0) {
            group = systemGroupService.findById(groupId);
            List<String> strIdList = Arrays.asList(group.getMenus().split(","));
            for (String strId : strIdList) {
                idList.add(Integer.parseInt(strId));
            }
        }
        List<SystemMenu> allMenu = systemMenuService.findAll();
        List<SystemMenuDto> systemMenuDtoList = new ArrayList<>();
        for (SystemMenu item : allMenu) {
            SystemMenuDto systemMenuDto = new SystemMenuDto();
            systemMenuDto.setId(item.getId());
            systemMenuDto.setName(item.getName());
            systemMenuDto.setTopid(item.getTopid());
            systemMenuDto.setLink(item.getLink());
            systemMenuDtoList.add(systemMenuDto);
        }
        List<SystemMenuDto> rootMenu = new ArrayList<>();
        for (SystemMenuDto item : systemMenuDtoList) {
            if (item.getTopid().equals(0)) {
                rootMenu.add(item);
            }
        }
        for (SystemMenuDto item : rootMenu) {
            List<SystemMenuDto> children = getChildren(item.getId(), systemMenuDtoList);
            item.setChildren(children);
        }
        return rootMenu;
    }

    public List<SystemMenuDto> getByGroupId(int groupId) {
        List<SystemMenu> allMenu = systemMenuService.findAll();

        Map<Integer, SystemMenu> mappedAllMenu = new HashMap<>();
        for (SystemMenu item : allMenu) {
            mappedAllMenu.put(item.getId(), item);
        }

        List<Integer> menuIdList = new ArrayList<>();

        if (groupId != 1) {
            SystemGroup systemGroup = systemGroupService.findById(groupId);
            List<String> strIdList = Arrays.asList(systemGroup.getMenus().split(","));
            List<Integer> intIdList = new ArrayList<>();
            for (String strId : strIdList) {
                intIdList.add(Integer.parseInt(strId));
            }
            List<SystemMenu> menuInfoList = systemMenuService.findByIdIn(intIdList);
            if (menuInfoList.isEmpty()) {
                return new ArrayList<SystemMenuDto>();
            }
            for(SystemMenu item : menuInfoList) {
                if (item.getLevel() == 2) {
                    intIdList.add(item.getTopid());
                }
                if (item.getLevel() == 3) {
                    intIdList.add(item.getTopid());
                    SystemMenu topMenu = mappedAllMenu.get(item.getTopid());
                    intIdList.add(topMenu.getTopid());
                }
            }
            menuIdList = intIdList.stream().distinct().collect(Collectors.toList());
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uri = request.getRequestURI();

        List<SystemMenuDto> systemMenuDtoList = new ArrayList<>();
        List<Integer> activeIdList = new ArrayList<>();
        for (SystemMenu item : allMenu) {
            if (groupId == 1 || menuIdList.contains(item.getId())) {
                SystemMenuDto systemMenuDto = new SystemMenuDto();
                systemMenuDto.setId(item.getId());
                systemMenuDto.setName(item.getName());
                systemMenuDto.setTopid(item.getTopid());
                systemMenuDto.setLink(item.getLink());
                if (StringUtils.isNotBlank(item.getPath()) && uri.indexOf(item.getPath()) != -1) {
                    activeIdList.add(item.getId());
                    SystemMenu level2Menu = mappedAllMenu.get(item.getTopid());
                    if (level2Menu != null) {
                        activeIdList.add(level2Menu.getId());
                        SystemMenu level1Menu = mappedAllMenu.get(level2Menu.getTopid());
                        if (level1Menu != null) {
                            activeIdList.add(level1Menu.getId());
                        }
                    }
                }
                systemMenuDtoList.add(systemMenuDto);
            }
        }
        List<SystemMenuDto> rootMenu = new ArrayList<>();
        for (SystemMenuDto item : systemMenuDtoList) {
            if (item.getTopid().equals(0)) {
                rootMenu.add(item);
            }
        }
        for (SystemMenuDto item : rootMenu) {
            List<SystemMenuDto> children = getChildren(item.getId(), systemMenuDtoList);
            item.setChildren(children);
        }
        return rootMenu;
    }

    private List<SystemMenuDto> getChildren(Integer id, List<SystemMenuDto> allMenu) {
        List<SystemMenuDto> children = new ArrayList<>();
        for (SystemMenuDto item : allMenu) {
            if (item.getTopid().equals(id)) {
                children.add(item);
            }
        }
        for (SystemMenuDto item : children) {
            item.setChildren(getChildren(item.getId(), allMenu));
        }
        if (children.size() == 0) {
            return new ArrayList<SystemMenuDto>();
        }
        return children;
    }
}
