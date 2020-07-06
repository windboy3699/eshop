package com.example.eshop.admin.controller;

import com.example.eshop.admin.domain.SystemMenu;
import com.example.eshop.admin.dto.ResponseDto;
import com.example.eshop.admin.enums.ErrorCodeEnum;
import com.example.eshop.admin.service.SystemMenuService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class SystemMenuController {
    @Autowired
    private SystemMenuService systemMenuService;

    private List<Map<String, String>> getBaseBreadCrumbs() {
        List<Map<String, String>> breadCrumbs = new ArrayList<>();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "系统管理");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);
        return breadCrumbs;
    }

    @RequestMapping("/system/menu")
    public String index(Model model, @RequestParam(required = false, defaultValue = "0") Integer topid) {

        PaginatorServiceImpl paginatorServiceImpl = new PaginatorServiceImpl(10,5,"page");
        Integer pageNum = paginatorServiceImpl.getPageNum() - 1;
        Integer pageSize = paginatorServiceImpl.getPageSize();

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "sort"));
        Page<SystemMenu> pageSystemMenu = systemMenuService.findByTopid(topid, pageable);

        List<SystemMenu> list = pageSystemMenu.getContent();
        Map<String, Object> paginator = paginatorServiceImpl.paging(pageSystemMenu.getTotalElements());

        Boolean addMenu = true;
        if (topid > 0) {
            SystemMenu topMenu = systemMenuService.findById(topid);
            if (topMenu.getLevel() > 2) {
                addMenu = false;
            }
        }

        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "菜单管理");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        model.addAttribute("topid", topid);
        model.addAttribute("addMenu", addMenu);
        model.addAttribute("list", list);
        model.addAttribute("paginator", paginator);
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "system/menu";
    }

    @RequestMapping("/system/menu/add")
    public String add(Model model, @RequestParam(required = false, defaultValue = "0") Integer topid) {
        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "菜单添加");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        model.addAttribute("menu", null);
        model.addAttribute("topid", topid);
        model.addAttribute("sort", 100);
        model.addAttribute("visible", 1);
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "system/menuEdit";
    }

    @RequestMapping("/system/menu/edit")
    public String edit(Model model, @RequestParam Integer id) {
        SystemMenu menu = systemMenuService.findById(id);

        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "菜单编辑");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        model.addAttribute("menu", menu);
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "system/menuEdit";
    }

    @RequestMapping("/system/menu/save")
    @ResponseBody
    public ResponseDto<Object> save(SystemMenu menu) {
        if (StringUtils.isBlank(menu.getName())) {
            return ResponseDto.create(ErrorCodeEnum.MISSING_PARAM.getCode(), ErrorCodeEnum.MISSING_PARAM.getMessage());
        }
        if (menu.getTopid() == 0) {
            menu.setLevel(1);
        } else {
            SystemMenu topMenu = systemMenuService.findById(menu.getTopid());
            if (topMenu.getTopid() == 0) {
                menu.setLevel(2);
            } else {
                menu.setLevel(3);
            }
        }
        systemMenuService.save(menu);

        return ResponseDto.create(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMessage());
    }
}
