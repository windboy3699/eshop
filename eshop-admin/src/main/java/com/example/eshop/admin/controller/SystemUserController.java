package com.example.eshop.admin.controller;

import com.example.eshop.admin.domain.SystemUser;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class SystemUserController extends BaseController {
    @Autowired
    private SystemUserService systemUserService;

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

        model.addAttribute("list", list);
        model.addAttribute("paginator", paginator);
        model.addAttribute("currentUrl", currentUrl);

        return "system/user";
    }
}
