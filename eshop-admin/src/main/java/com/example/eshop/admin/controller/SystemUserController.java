package com.example.eshop.admin.controller;

import com.example.eshop.admin.domain.SystemGroup;
import com.example.eshop.admin.domain.SystemUser;
import com.example.eshop.admin.dto.ResultDto;
import com.example.eshop.admin.dto.TokenInfoDto;
import com.example.eshop.admin.service.LoginService;
import com.example.eshop.admin.service.SystemGroupService;
import com.example.eshop.admin.service.SystemUserService;
import com.example.eshop.admin.service.impl.PaginatorServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class SystemUserController extends BaseController {
    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SystemGroupService systemGroupService;

    @Autowired
    private LoginService loginService;

    @RequestMapping("/system/user")
    public String index(Model model) {
        PaginatorServiceImpl paginatorServiceImpl = new PaginatorServiceImpl(2,5,"page");
        Integer pageNum = paginatorServiceImpl.getPageNum() - 1;
        Integer pageSize = paginatorServiceImpl.getPageSize();

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<SystemUser> pageSystemUser = systemUserService.findAll(pageable);

        List<SystemUser> list = pageSystemUser.getContent();
        Map<String, Object> paginator = paginatorServiceImpl.paging(pageSystemUser.getTotalElements());

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

    @RequestMapping(value = "/system/user/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> save(@Valid SystemUser user, BindingResult result) throws JsonProcessingException {
        ResultDto<String> resultDto = new ResultDto<>();
        if (result.hasErrors()) {
            resultDto.setCode(101);
            resultDto.setMsg("参数错误");
            return resultDto;
        }

        if (user.getId() == null) {
            if (user.getPassword().length() == 0) {
                resultDto.setCode(102);
                resultDto.setMsg("缺少密码");
                return resultDto;
            }

            SystemUser existUser = systemUserService.findByUsername(user.getUsername());
            if (existUser != null) {
                resultDto.setCode(103);
                resultDto.setMsg("用户名已存在");
                return resultDto;
            }

            TokenInfoDto tokenInfoDto = loginService.checkLogin();
            user.setAddUser(tokenInfoDto.getSystemUsername());

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = df.format(new Date());
            user.setCreateTime(time);
        }

        if (user.getPassword().length() != 0) {
            String password = user.getPassword();
            String md5Pwd = DigestUtils.md5DigestAsHex(password.getBytes());
            user.setPassword(md5Pwd);
        }

        systemUserService.save(user);

        resultDto.setCode(0);
        resultDto.setMsg("保存成功");
        return resultDto;
    }

    @RequestMapping(value = "/system/user/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<String> delete(@RequestParam Integer id) {
        systemUserService.deleteById(id);
        ResultDto<String> resultDto = new ResultDto<>();
        resultDto.setCode(0);
        resultDto.setMsg("删除成功");
        return resultDto;
    }
}
