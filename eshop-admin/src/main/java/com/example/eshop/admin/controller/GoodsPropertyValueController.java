package com.example.eshop.admin.controller;

import com.example.eshop.admin.domain.GoodsProperty;
import com.example.eshop.admin.domain.GoodsPropertyValue;
import com.example.eshop.admin.dto.ResponseDto;
import com.example.eshop.admin.enums.ErrorCodeEnum;
import com.example.eshop.admin.service.GoodsCategoryService;
import com.example.eshop.admin.service.GoodsPropertyService;
import com.example.eshop.admin.service.GoodsPropertyValueService;
import org.apache.commons.lang.StringUtils;
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
public class GoodsPropertyValueController {
    @Autowired
    private GoodsPropertyValueService goodsPropertyValueService;

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

    @RequestMapping("/goods/category/property/value")
    public String index(Model model, @RequestParam Integer propertyId) {
        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "属性值管理");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        List<GoodsPropertyValue> list = goodsPropertyValueService.findByPropertyId(propertyId);
        GoodsProperty goodsProperty = goodsPropertyService.findById(propertyId);

        model.addAttribute("list", list);
        model.addAttribute("propertyId", propertyId);
        model.addAttribute("propertyName", goodsProperty.getName());
        model.addAttribute("categoryId", goodsProperty.getCategoryId());
        model.addAttribute("categoryFullName", goodsCategoryService.getFullJoinName(goodsProperty.getCategoryId()));
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "goods/propertyValue";
    }

    @RequestMapping("/goods/category/property/value/add")
    public String add(Model model, @RequestParam Integer propertyId) {
        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "属性值添加");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        GoodsProperty goodsProperty = goodsPropertyService.findById(propertyId);

        model.addAttribute("propertyValue", null);
        model.addAttribute("propertyId", propertyId);
        model.addAttribute("propertyName", goodsProperty.getName());
        model.addAttribute("categoryFullName", goodsCategoryService.getFullJoinName(goodsProperty.getCategoryId()));
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "goods/propertyValueEdit";
    }

    @RequestMapping("/goods/category/property/value/edit")
    public String edit(Model model, @RequestParam Integer id) {
        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "属性值编辑");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        GoodsPropertyValue goodsPropertyValue = goodsPropertyValueService.findById(id);
        GoodsProperty goodsProperty = goodsPropertyService.findById(goodsPropertyValue.getPropertyId());

        model.addAttribute("propertyValue", goodsPropertyValue);
        model.addAttribute("propertyId", goodsProperty.getId());
        model.addAttribute("propertyName", goodsProperty.getName());
        model.addAttribute("categoryFullName", goodsCategoryService.getFullJoinName(goodsProperty.getCategoryId()));
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "goods/propertyValueEdit";
    }

    @RequestMapping("/goods/category/property/value/save")
    @ResponseBody
    public ResponseDto<Object> save(GoodsPropertyValue goodsPropertyValue) {
        if (StringUtils.isBlank(goodsPropertyValue.getName())) {
            return ResponseDto.create(ErrorCodeEnum.MISSING_PARAM.getCode(), ErrorCodeEnum.MISSING_PARAM.getMessage());
        }
        goodsPropertyValueService.save(goodsPropertyValue);
        return ResponseDto.create(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMessage());
    }
}
