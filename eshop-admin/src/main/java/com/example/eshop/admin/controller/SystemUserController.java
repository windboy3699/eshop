package com.example.eshop.admin.controller;

import com.example.eshop.admin.domain.SystemGroup;
import com.example.eshop.admin.domain.SystemUser;
import com.example.eshop.admin.dto.ResponseDto;
import com.example.eshop.admin.dto.TokenInfoDto;
import com.example.eshop.admin.enums.ErrorCodeEnum;
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

    private List<Map<String, String>> getBaseBreadCrumbs() {
        List<Map<String, String>> breadCrumbs = new ArrayList<>();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "系统管理");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);
        return breadCrumbs;
    }

    @RequestMapping("/system/user")
    public String index(Model model) {
        PaginatorServiceImpl paginatorServiceImpl = new PaginatorServiceImpl(10,5,"page");
        Integer pageNum = paginatorServiceImpl.getPageNum() - 1;
        Integer pageSize = paginatorServiceImpl.getPageSize();

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<SystemUser> pageSystemUser = systemUserService.findAll(pageable);

        List<SystemUser> list = pageSystemUser.getContent();
        Map<String, Object> paginator = paginatorServiceImpl.paging(pageSystemUser.getTotalElements());

        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "用户管理");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        model.addAttribute("list", list);
        model.addAttribute("paginator", paginator);
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "system/user";
    }

    @RequestMapping("/system/user/add")
    public String add(Model model) {
        List<SystemGroup> groupList = systemGroupService.findAll();

        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "用户添加");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        model.addAttribute("user", null);
        model.addAttribute("groupList", groupList);
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "system/userEdit";
    }

    @RequestMapping(value = "/system/user/edit", method = RequestMethod.GET)
    public String edit(Model model, @RequestParam Integer id) {
        SystemUser user = systemUserService.findById(id);

        List<SystemGroup> groupList = systemGroupService.findAll();

        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "用户编辑");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        model.addAttribute("user", user);
        model.addAttribute("groupList", groupList);
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "system/userEdit";
    }

    @RequestMapping(value = "/system/user/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto<Object> save(@Valid SystemUser user, BindingResult result) throws JsonProcessingException {
        if (result.hasErrors()) {
            return createResponseDto(ErrorCodeEnum.MISSING_PARAM.getCode(), ErrorCodeEnum.MISSING_PARAM.getMessage());
        }
        if (user.getId() == null) {
            if (user.getPassword().length() == 0) {
                return createResponseDto(ErrorCodeEnum.MISSING_PARAM.getCode(), ErrorCodeEnum.MISSING_PARAM.getMessage());
            }
            SystemUser existUser = systemUserService.findByUsername(user.getUsername());
            if (existUser != null) {
                return createResponseDto(50101, "用户名已存在");
            }
            TokenInfoDto tokenInfoDto = loginService.checkLogin();
            user.setAddUser(tokenInfoDto.getSystemUsername());

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = df.format(new Date());
            user.setCreateTime(time);
        }
        if (user.getPassword().length() == 0) {
            user.setPassword(null);
        } else {
            String password = user.getPassword();
            String md5Pwd = DigestUtils.md5DigestAsHex(password.getBytes());
            user.setPassword(md5Pwd);
        }
        systemUserService.save(user);
        return createResponseDto(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMessage());
    }

    @RequestMapping(value = "/system/user/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto<Object> delete(@RequestParam Integer id) {
        systemUserService.deleteById(id);
        return createResponseDto(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMessage());
    }
}
