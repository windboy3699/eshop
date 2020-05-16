package com.example.eshop.admin.controller;

import com.example.eshop.admin.domain.SystemMenu;
import com.example.eshop.admin.service.SystemMenuService;
import com.example.eshop.admin.service.impl.PaginatorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class SystemMenuController {
    @Autowired
    private SystemMenuService systemMenuService;

    @RequestMapping("/system/menu")
    public String index(Model model, @RequestParam(required = false, defaultValue = "0") Integer topid) {

        PaginatorServiceImpl paginatorServiceImpl = new PaginatorServiceImpl(2,5,"page");
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

        List<Map<String, String>> breadCrumbs = new ArrayList<>();
        Map<String, String> crumbs1 = new HashMap<>();
        crumbs1.put("name", "系统管理");
        crumbs1.put("link", "");
        breadCrumbs.add(crumbs1);
        Map<String, String> crumbs2 = new HashMap<>();
        crumbs2.put("name", "菜单管理");
        crumbs2.put("link", "");
        breadCrumbs.add(crumbs2);

        model.addAttribute("topid", topid);
        model.addAttribute("addMenu", addMenu);
        model.addAttribute("list", list);
        model.addAttribute("paginator", paginator);
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "system/menu";
    }
}
