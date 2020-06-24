package com.example.eshop.admin.controller;

import com.example.eshop.admin.domain.GoodsCategory;
import com.example.eshop.admin.dto.ResponseDto;
import com.example.eshop.admin.enums.ErrorCodeEnum;
import com.example.eshop.admin.service.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class GoodsCategoryController {
    @Autowired
    private GoodsCategoryService goodsCategoryService;

    private List<Map<String, String>> getBaseBreadCrumbs() {
        List<Map<String, String>> breadCrumbs = new ArrayList<>();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "商城系统");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);
        return breadCrumbs;
    }

    @RequestMapping("/goods/category")
    public String index(Model model, @RequestParam(required = false, defaultValue = "0") Integer parentId) {
        List<GoodsCategory> list = goodsCategoryService.findByParentId(parentId);

        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "分类管理");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        model.addAttribute("list", list);
        model.addAttribute("parentId", parentId);
        model.addAttribute("breadCrumbs", breadCrumbs);
        return "goods/category";
    }

    @RequestMapping("/goods/category/add")
    public String add(Model model, @RequestParam(required = false, defaultValue = "0") Integer parentId) {
        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "分类添加");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        model.addAttribute("category", null);
        model.addAttribute("parentId", parentId);
        model.addAttribute("sort", 100);
        model.addAttribute("breadCrumbs", breadCrumbs);
        return "goods/categoryEdit";
    }

    @RequestMapping("/goods/category/edit")
    public String edit(Model model, @RequestParam Integer id) {
        GoodsCategory category = goodsCategoryService.findById(id);

        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "分类编辑");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        model.addAttribute("category", category);
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "goods/categoryEdit";
    }

    @RequestMapping("/goods/category/save")
    @ResponseBody
    public ResponseDto<Object> save(GoodsCategory category) {
        if (category.getName() == null || category.getName().length() == 0) {
            return ResponseDto.create(ErrorCodeEnum.MISSING_PARAM.getCode(), ErrorCodeEnum.MISSING_PARAM.getMessage());
        }
        goodsCategoryService.save(category);

        return ResponseDto.create(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMessage());
    }
}
