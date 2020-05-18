package com.example.eshop.admin.controller;

import com.example.eshop.admin.domain.SystemGroup;
import com.example.eshop.admin.dto.ResponseDto;
import com.example.eshop.admin.dto.SystemMenuDto;
import com.example.eshop.admin.enums.ErrorCodeEnum;
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
public class SystemGroupController extends BaseController {
    @Autowired
    private SystemGroupService systemGroupService;

    @Autowired
    private SystemMultiStageMenuService systemMultiStageMenuService;

    private List<Map<String, String>> getBaseBreadCrumbs() {
        List<Map<String, String>> breadCrumbs = new ArrayList<>();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "系统管理");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);
        return breadCrumbs;
    }

    @RequestMapping("/system/group")
    public String index(Model model) {
        PaginatorServiceImpl paginatorServiceImpl = new PaginatorServiceImpl(10,5,"page");
        Integer pageNum = paginatorServiceImpl.getPageNum() - 1;
        Integer pageSize = paginatorServiceImpl.getPageSize();

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<SystemGroup> pageSystemGroup = systemGroupService.findAll(pageable);

        List<SystemGroup> list = pageSystemGroup.getContent();
        Map<String, Object> paginator = paginatorServiceImpl.paging(pageSystemGroup.getTotalElements());

        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "用户组管理");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        model.addAttribute("list", list);
        model.addAttribute("paginator", paginator);
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "system/group";
    }

    @RequestMapping("/system/group/add")
    public String add(Model model) {
        List<SystemMenuDto> systemMenuDtoList = systemMultiStageMenuService.getAll(0);

        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "用户组添加");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        model.addAttribute("group", null);
        model.addAttribute("allMenu", systemMenuDtoList);
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "system/groupEdit";
    }

    @RequestMapping("/system/group/edit")
    public String edit(Model model, @RequestParam Integer id) {
        SystemGroup group = systemGroupService.findById(id);

        List<SystemMenuDto> systemMenuDtoList = systemMultiStageMenuService.getAll(id);

        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "用户组编辑");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        model.addAttribute("group", group);
        model.addAttribute("allMenu", systemMenuDtoList);
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "system/groupEdit";
    }

    @RequestMapping(value = "/system/group/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto<Object> save(@RequestParam(required = false) Integer id, @RequestParam String name, @RequestParam("menus[]") List<String> menus) {
        if (name.length() == 0 || menus.isEmpty()) {
            return createResponseDto(ErrorCodeEnum.MISSING_PARAM.getCode(), ErrorCodeEnum.MISSING_PARAM.getMessage());
        }
        SystemGroup group = new SystemGroup();
        group.setName(name);
        group.setMenus(StringUtils.join(menus, ","));
        if (id != null) {
            group.setId(id);
        }
        systemGroupService.save(group);
        return createResponseDto(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMessage());
    }
}
