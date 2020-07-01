package com.example.eshop.admin.controller;

import com.example.eshop.admin.domain.Goods;
import com.example.eshop.admin.domain.GoodsCategory;
import com.example.eshop.admin.domain.GoodsProperty;
import com.example.eshop.admin.domain.GoodsPropertyValue;
import com.example.eshop.admin.dto.ResponseDto;
import com.example.eshop.admin.enums.ErrorCodeEnum;
import com.example.eshop.admin.service.GoodsCategoryService;
import com.example.eshop.admin.service.GoodsPropertyService;
import com.example.eshop.admin.service.GoodsPropertyValueService;
import com.example.eshop.admin.service.GoodsService;
import com.example.eshop.admin.service.impl.PaginatorServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

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

        Map<Integer, String> allPropertyValueName = goodsPropertyValueService.getAllNameMap();
        Map<Integer, String> goodsPropertyValueMap = new HashMap<>();
        for (Goods item : list) {
            if (item.getProperties().isEmpty()) {
                goodsPropertyValueMap.put(item.getId(), "");
                continue;
            }
            String names = "";
            for (Integer propId : item.getProperties()) {
                if (names.length() == 0) {
                    names += allPropertyValueName.get(propId);
                } else {
                    names += "," + allPropertyValueName.get(propId);
                }
            }
            goodsPropertyValueMap.put(item.getId(), names);
        }

        model.addAttribute("list", list);
        model.addAttribute("allCategoryMap", goodsCategoryService.getAllCategoryMap());
        model.addAttribute("goodsPropertyValueMap", goodsPropertyValueMap);
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
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "goods/goodsEdit";
    }

    @RequestMapping("/goods/goods/edit")
    public String edit(Model model, @RequestParam Integer id) {
        List<Map<String, String>> breadCrumbs = getBaseBreadCrumbs();
        Map<String, String> crumbs = new HashMap<>();
        crumbs.put("name", "商品编辑");
        crumbs.put("link", "");
        breadCrumbs.add(crumbs);

        Goods goods = goodsService.findById(id);

        model.addAttribute("goods", goods);
        model.addAttribute("categoryId", goods.getCategoryId());
        model.addAttribute("properties", StringUtils.join(goods.getProperties(), "_"));
        model.addAttribute("breadCrumbs", breadCrumbs);

        return "goods/goodsEdit";
    }

    @RequestMapping("/goods/goods/selectCategoryAndProperty")
    public String selectCategoryAndProperty(Model model, @RequestParam Integer categoryId, @RequestParam(required = false, defaultValue = "") String properties) {
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
        List<String> selectedIdList = new ArrayList<>();
        for (Integer i : parentsIdList) {
            selectedIdList.add(String.valueOf(i));
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

        List<Integer> propertyIdList = new ArrayList<>();
        if (properties != null && properties.length() != 0) {
            List<String> splitString = Arrays.asList(properties.split("_"));
            for (String str : splitString) {
                propertyIdList.add(Integer.valueOf(str));
            }
        }

        model.addAttribute("categoryListGroup", categoryListGroup);
        model.addAttribute("selectedIdList", selectedIdList);
        model.addAttribute("goodsPropertyValueListGroup", goodsPropertyValueListGroup);
        model.addAttribute("propertyNameMap", propertyNameMap);
        model.addAttribute("propertyIdList", propertyIdList);

        return "goods/selectCategoryAndProperty";
    }

    @RequestMapping("/goods/goods/save")
    @ResponseBody
    public ResponseDto<Object> save(Goods goods, @RequestParam String introduction) {
        if (goods.getCategoryId() == null || goods.getPrice() == null || goods.getStock() == null) {
            return ResponseDto.create(ErrorCodeEnum.MISSING_PARAM.getCode(), ErrorCodeEnum.MISSING_PARAM.getMessage());
        }
        if (goods.getName().length() == 0 || goods.getImage().length() == 0 || introduction.length() == 0) {
            return ResponseDto.create(ErrorCodeEnum.MISSING_PARAM.getCode(), ErrorCodeEnum.MISSING_PARAM.getMessage());
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        goods.setCreated(time);
        goodsService.save(goods);

        return ResponseDto.create(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMessage());
    }
}
