package com.example.eshop.admin.controller;

import com.example.eshop.admin.domain.GoodsCategory;
import com.example.eshop.admin.domain.GoodsProperty;
import com.example.eshop.admin.dto.ResponseDto;
import com.example.eshop.admin.enums.ErrorCodeEnum;
import com.example.eshop.admin.service.GoodsCategoryService;
import com.example.eshop.admin.service.GoodsPropertyService;
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
public class GoodsPropertyController {
    @Autowired
    private GoodsPropertyService goodsPropertyService;

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

    @RequestMapping("/goods/category/property")
    public String index(Model model, @RequestParam Integer categoryId) {
        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "属性管理");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        List<GoodsProperty> list = goodsPropertyService.findByCategoryId(categoryId);
        GoodsCategory goodsCategory = goodsCategoryService.findById(categoryId);

        model.addAttribute("list", list);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categoryParentId", goodsCategory.getParentId());
        model.addAttribute("categoryFullName", goodsCategoryService.getFullJoinName(categoryId));
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "goods/property";
    }

    @RequestMapping("/goods/category/property/add")
    public String add(Model model, @RequestParam Integer categoryId) {
        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "属性添加");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        model.addAttribute("property", null);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categoryFullName", goodsCategoryService.getFullJoinName(categoryId));
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "goods/propertyEdit";
    }

    @RequestMapping("/goods/category/property/edit")
    public String edit(Model model, @RequestParam Integer id) {
        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "属性编辑");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        GoodsProperty property = goodsPropertyService.findById(id);

        model.addAttribute("property", property);
        model.addAttribute("breadCrumbs", breadCrumbs);
        model.addAttribute("categoryId", property.getCategoryId());
        model.addAttribute("categoryFullName", goodsCategoryService.getFullJoinName(property.getCategoryId()));

        return "goods/propertyEdit";
    }

    @RequestMapping("/goods/category/property/save")
    @ResponseBody
    public ResponseDto<Object> save(GoodsProperty property) {
        if (property.getName() == null || property.getName().length() == 0) {
            return ResponseDto.create(ErrorCodeEnum.MISSING_PARAM.getCode(), ErrorCodeEnum.MISSING_PARAM.getMessage());
        }
        goodsPropertyService.save(property);
        return ResponseDto.create(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMessage());
    }
}
