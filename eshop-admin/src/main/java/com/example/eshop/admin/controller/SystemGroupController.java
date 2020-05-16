package com.example.eshop.admin.controller;

import com.example.eshop.admin.domain.SystemGroup;
import com.example.eshop.admin.dto.ResultDto;
import com.example.eshop.admin.dto.SystemMenuDto;
import com.example.eshop.admin.service.SystemGroupService;
import com.example.eshop.admin.service.SystemMultiStageMenuService;
import com.example.eshop.admin.service.impl.PaginatorServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class SystemGroupController {
    @Autowired
    private SystemGroupService systemGroupService;

    @Autowired
    private SystemMultiStageMenuService systemMultiStageMenuService;

    @RequestMapping("/system/group")
    public String index(Model model) {
        PaginatorServiceImpl paginatorServiceImpl = new PaginatorServiceImpl(2,5,"page");
        Integer pageNum = paginatorServiceImpl.getPageNum() - 1;
        Integer pageSize = paginatorServiceImpl.getPageSize();

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<SystemGroup> pageSystemGroup = systemGroupService.findAll(pageable);

        List<SystemGroup> list = pageSystemGroup.getContent();
        Map<String, Object> paginator = paginatorServiceImpl.paging(pageSystemGroup.getTotalElements());

        List<Map<String, String>> breadCrumbs = new ArrayList<>();
        Map<String, String> crumbs1 = new HashMap<>();
        crumbs1.put("name", "系统管理");
        crumbs1.put("link", "");
        breadCrumbs.add(crumbs1);
        Map<String, String> crumbs2 = new HashMap<>();
        crumbs2.put("name", "用户组管理");
        crumbs2.put("link", "");
        breadCrumbs.add(crumbs2);

        model.addAttribute("list", list);
        model.addAttribute("paginator", paginator);
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "system/group";
    }

    @RequestMapping("/system/group/add")
    public String add(Model model) {
        List<Map<String, String>> breadCrumbs = new ArrayList<>();
        Map<String, String> crumbs1 = new HashMap<>();
        crumbs1.put("name", "系统管理");
        crumbs1.put("link", "");
        breadCrumbs.add(crumbs1);
        Map<String, String> crumbs2 = new HashMap<>();
        crumbs2.put("name", "用户组添加");
        crumbs2.put("link", "");
        breadCrumbs.add(crumbs2);

        List<SystemMenuDto> systemMenuDtoList = systemMultiStageMenuService.getAll(0);

        model.addAttribute("group", null);
        model.addAttribute("breadCrumbs", breadCrumbs);
        model.addAttribute("allMenu", systemMenuDtoList);

        return "system/groupEdit";
    }

    @RequestMapping("/system/group/edit")
    public String edit(Model model, @RequestParam Integer id) {
        List<Map<String, String>> breadCrumbs = new ArrayList<>();
        Map<String, String> crumbs1 = new HashMap<>();
        crumbs1.put("name", "系统管理");
        crumbs1.put("link", "");
        breadCrumbs.add(crumbs1);
        Map<String, String> crumbs2 = new HashMap<>();
        crumbs2.put("name", "用户组编辑");
        crumbs2.put("link", "");
        breadCrumbs.add(crumbs2);

        SystemGroup group = systemGroupService.findById(id);

        List<SystemMenuDto> systemMenuDtoList = systemMultiStageMenuService.getAll(id);

        model.addAttribute("group", group);
        model.addAttribute("breadCrumbs", breadCrumbs);
        model.addAttribute("allMenu", systemMenuDtoList);

        return "system/groupEdit";
    }

    @RequestMapping(value = "/system/group/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> save(@RequestParam(required = false) Integer id, @RequestParam String name, @RequestParam("menus[]") List<String> menus) {
        ResultDto<String> resultDto = new ResultDto<>();
        if (name.length() == 0 || menus.isEmpty()) {
            resultDto.setCode(101);
            resultDto.setMsg("缺少参数");
            return resultDto;
        }
        SystemGroup group = new SystemGroup();
        group.setName(name);
        group.setMenus(StringUtils.join(menus, ","));
        if (id != null) {
            group.setId(id);
        }
        systemGroupService.save(group);
        resultDto.setCode(0);
        resultDto.setMsg("保存成功");
        return resultDto;
    }
}
