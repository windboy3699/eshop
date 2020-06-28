package com.example.eshop.admin.controller;

import com.example.eshop.admin.domain.GoodsCategory;
import com.example.eshop.admin.domain.GoodsProperty;
import com.example.eshop.admin.service.GoodsCategoryService;
import com.example.eshop.admin.service.GoodsPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class GoodsPropertyController {
    @Autowired
    private GoodsPropertyService goodsPropertyService;

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @RequestMapping("/goods/property")
    public String index(Model model, @RequestParam Integer categoryId) {
        List<GoodsProperty> list = goodsPropertyService.findByCategoryId(categoryId);
        GoodsCategory goodsCategory = goodsCategoryService.findById(categoryId);
        String categoryName = goodsCategoryService.getParentsJoinName(goodsCategory.getParentId());
        categoryName += " > " + goodsCategory.getName();

        model.addAttribute("list", list);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categoryName", categoryName);

        return "goods/property";
    }
}
