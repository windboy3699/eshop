package com.example.eshop.admin.controller;

import com.example.eshop.admin.domain.Goods;
import com.example.eshop.admin.domain.GoodsCategory;
import com.example.eshop.admin.domain.GoodsProperty;
import com.example.eshop.admin.domain.GoodsPropertyValue;
import com.example.eshop.admin.service.GoodsCategoryService;
import com.example.eshop.admin.service.GoodsPropertyService;
import com.example.eshop.admin.service.GoodsPropertyValueService;
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
import org.springframework.web.bind.annotation.RequestParam;

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

    @Autowired
    private GoodsPropertyService goodsPropertyService;

    @Autowired
    private GoodsPropertyValueService goodsPropertyValueService;

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

        model.addAttribute("categoryId", 0);

        return "goods/goodsEdit";
    }

    @RequestMapping("/goods/goods/edit")
    public String edit(Model model, @RequestParam Integer id) {
        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "商品编辑");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        model.addAttribute("categoryId", id);

        return "goods/goodsEdit";
    }

    @RequestMapping("/goods/goods/selectCategoryAndProperty")
    public String getCategoryMenu(Model model, @RequestParam Integer categoryId) {
        List<Integer> parentsIdList = new ArrayList<>();
        parentsIdList.add(0);
        if (categoryId > 0) {
            List<Integer> someParentsIdList = goodsCategoryService.getParentsIdList(categoryId, new ArrayList<Integer>());
            parentsIdList.addAll(someParentsIdList);
        }

        List<List<Map<String, String>>> categoryListGroup = new ArrayList<>();
        for (Integer pid : parentsIdList) {
            List<GoodsCategory> categoryList = goodsCategoryService.findByParentId(pid);
            if (categoryList.isEmpty()) {
                continue;
            }
            List<Map<String, String>> mapList = new ArrayList<>();
            for (GoodsCategory cate : categoryList) {
                Map<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(cate.getId()));
                map.put("name", cate.getName());
                mapList.add(map);
            }
            categoryListGroup.add(mapList);
        }
        List<String> selectedId = new ArrayList<>();
        for (Integer i : parentsIdList) {
            selectedId.add(String.valueOf(i));
        }

        Map<Integer, String> propertyNameMap = new HashMap<>();
        List<List<GoodsPropertyValue>> goodsPropertyValueListGroup = new ArrayList<>();
        if (categoryId > 0) {
            List<GoodsProperty> goodsPropertyList = goodsPropertyService.findByCategoryId(categoryId);
            for (GoodsProperty goodsProperty : goodsPropertyList) {
                propertyNameMap.put(goodsProperty.getId(), goodsProperty.getName());
                List<GoodsPropertyValue> goodsPropertyValueList = goodsPropertyValueService.findByPropertyId(goodsProperty.getId());
                if (!goodsPropertyValueList.isEmpty()) {
                    goodsPropertyValueListGroup.add(goodsPropertyValueList);
                }
            }
        }

        model.addAttribute("categoryListGroup", categoryListGroup);
        model.addAttribute("selectedId", selectedId);
        model.addAttribute("goodsPropertyValueListGroup", goodsPropertyValueListGroup);
        model.addAttribute("propertyNameMap", propertyNameMap);

        return "goods/selectCategoryAndProperty";
    }
}
