package com.example.eshop.admin.controller;

import com.example.eshop.admin.domain.Goods;
import com.example.eshop.admin.domain.GoodsCategory;
import com.example.eshop.admin.service.GoodsCategoryService;
import com.example.eshop.admin.service.GoodsService;
import com.example.eshop.admin.service.impl.PaginatorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

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

    @RequestMapping("/goods/goods")
    public String index(Model model) {
        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "商品列表");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        PaginatorServiceImpl paginatorServiceImpl = new PaginatorServiceImpl(10,5,"page");
        Integer pageNum = paginatorServiceImpl.getPageNum() - 1;
        Integer pageSize = paginatorServiceImpl.getPageSize();

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Goods> pageGoods = goodsService.findAll(pageable);
        List<Goods> list = pageGoods.getContent();
        Map<String, Object> paginator = paginatorServiceImpl.paging(pageGoods.getTotalElements());

        model.addAttribute("list", list);
        model.addAttribute("allCategoryMap", goodsCategoryService.getAllCategoryMap());
        model.addAttribute("paginator", paginator);
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "goods/goods";
    }

    @RequestMapping("/goods/goods/add")
    public String add(Model model) {
        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "商品添加");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        List<GoodsCategory> rootCategoryList = goodsCategoryService.findByParentId(0);

        model.addAttribute("rootCategoryList", rootCategoryList);

        return "goods/goodsEdit";
    }

    @RequestMapping("/goods/goods/edit")
    public String edit(Model model) {
        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "商品编辑");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        List<GoodsCategory> rootCategoryList = goodsCategoryService.findByParentId(0);

        model.addAttribute("rootCategoryList", rootCategoryList);

        return "goods/goodsEdit";
    }
}
