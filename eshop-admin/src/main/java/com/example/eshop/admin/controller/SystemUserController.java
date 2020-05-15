package com.example.eshop.admin.controller;

import com.example.eshop.admin.domain.SystemGroup;
import com.example.eshop.admin.domain.SystemUser;
import com.example.eshop.admin.service.SystemGroupService;
import com.example.eshop.admin.service.SystemUserService;
import com.example.eshop.admin.service.impl.PaginatorServiceImpl;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class SystemUserController extends BaseController {
    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SystemGroupService systemGroupService;

    @RequestMapping("/system/user")
    public String index(Model model) {
        PaginatorServiceImpl paginatorServiceImpl = new PaginatorServiceImpl(2,5,"page");
        Integer pageNum = paginatorServiceImpl.getPageNum() - 1;
        Integer pageSize = paginatorServiceImpl.getPageSize();

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<SystemUser> pageSystemUser = systemUserService.findAll(pageable);

        List<SystemUser> list = pageSystemUser.getContent();
        Map<String, Object> paginator = paginatorServiceImpl.paging(pageSystemUser.getTotalElements());

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String url = new String(request.getRequestURL());
        String queryString = request.getQueryString();
        String currentUrl = queryString == null ? url : url + "?" + queryString;

        List<Map<String, String>> breadCrumbs = new ArrayList<>();
        Map<String, String> crumbs1 = new HashMap<>();
        crumbs1.put("name", "系统管理");
        crumbs1.put("link", "");
        breadCrumbs.add(crumbs1);
        Map<String, String> crumbs2 = new HashMap<>();
        crumbs2.put("name", "用户管理");
        crumbs2.put("link", "");
        breadCrumbs.add(crumbs2);

        model.addAttribute("list", list);
        model.addAttribute("paginator", paginator);
        model.addAttribute("currentUrl", currentUrl);
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "system/user";
    }

    @RequestMapping("/system/user/add")
    public String add(Model model) {
        List<Map<String, String>> breadCrumbs = new ArrayList<>();
        Map<String, String> crumbs1 = new HashMap<>();
        crumbs1.put("name", "系统管理");
        crumbs1.put("link", "");
        breadCrumbs.add(crumbs1);
        Map<String, String> crumbs2 = new HashMap<>();
        crumbs2.put("name", "用户添加");
        crumbs2.put("link", "");
        breadCrumbs.add(crumbs2);

        List<SystemGroup> groupList = systemGroupService.findAll();

        model.addAttribute("user", null);
        model.addAttribute("groupList", groupList);
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "system/userEdit";
    }

    @RequestMapping(value = "/system/user/edit", method = RequestMethod.GET)
    public String edit(Model model, @RequestParam Integer id) {

        SystemUser user = systemUserService.findById(id);

        List<SystemGroup> groupList = systemGroupService.findAll();

        List<Map<String, String>> breadCrumbs = new ArrayList<>();
        Map<String, String> crumbs1 = new HashMap<>();
        crumbs1.put("name", "系统管理");
        crumbs1.put("link", "");
        breadCrumbs.add(crumbs1);
        Map<String, String> crumbs2 = new HashMap<>();
        crumbs2.put("name", "用户编辑");
        crumbs2.put("link", "");
        breadCrumbs.add(crumbs2);

        model.addAttribute("user", user);
        model.addAttribute("groupList", groupList);
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "system/userEdit";
    }

}
