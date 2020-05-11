package com.example.eshop.admin.service.impl;

import com.example.eshop.admin.service.PaginatorService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class PaginatorServiceImpl implements PaginatorService {
    private HttpServletRequest request;

    private Integer total; //条目总数
    private Integer pageNum; //当前页码
    private Integer pageTotal; //条目可分总页数
    private Integer pageSize = 10; //每页条目数量
    private Integer pageLength = 5; //显示的页码数量
    private String pageName = "page"; //页码参数名
    private String queryUrl; //带参数的url

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageLength(Integer pageLength) {
        this.pageLength = pageLength;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public Map<String, Object> paging(Integer total) {
        this.request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        this.total = total;
        pageTotal = (new Double(Math.ceil(this.total/pageSize))).intValue();
        handlePageNum();
        handleQueryUrl();

        Map<String, Object> map = new HashMap<>();

        map.put("total", this.total);

        String firstPageUrl = pageNum == 1 ? "" : buildUrl(1);
        map.put("firstPageUrl", firstPageUrl);

        String lastPageUrl = pageNum == pageTotal ? "" : buildUrl(pageTotal);
        map.put("lastPageUrl", lastPageUrl);

        String prevPageUrl = pageNum == 1 ? "" : buildUrl(pageNum - 1);
        map.put("prevPageUrl", prevPageUrl);

        String nextPageUrl = pageNum == pageTotal ? "" : buildUrl(pageNum + 1);
        map.put("nextPageUrl", nextPageUrl);

        map.put("middlePageUrl", middlePageUrl());

        return map;
    }

    private void handlePageNum() {
        String paramPageNum = request.getParameter(pageName);
        if (paramPageNum == null || paramPageNum.length() == 0) {
            pageNum = 1;
        } else {
            if (paramPageNum.matches("[0-9]+")) {
                pageNum = Integer.parseInt(paramPageNum);
            } else {
                pageNum = 1;
            }
        }
    }

    private void handleQueryUrl() {
        StringBuffer url = request.getRequestURL();
        String queryString = request.getQueryString();

        String[] queryStringArray = queryString.split("&");
        List<String> queryStringList = new ArrayList<>(queryStringArray.length);
        Collections.addAll(queryStringList, queryStringArray);

        if (queryStringList == null || queryStringList.isEmpty()) {
            queryUrl = url + "?";
        } else {
            String removeItem = null;
            for (String item : queryStringList) {
                if (item.indexOf(this.pageName+"=") != -1) {
                    removeItem = item;
                    break;
                }
            }
            if (removeItem != null) {
                queryStringList.remove(removeItem);
            }
            if (queryStringList.isEmpty()) {
                queryUrl = url + "?";
            } else {
                queryUrl = url + "?" + org.apache.commons.lang.StringUtils.join(queryStringList.toArray(), "&") + '&';
            }
        }
    }

    private List<Map<String, Object>> middlePageUrl() {
        Integer start,end;
        if (pageTotal <= pageLength) {
            start = 1;
            end = pageTotal;
        } else {
            double doubleLeftLength = Math.floor((pageLength-1)/2);
            Integer leftLength = (new Double(doubleLeftLength)).intValue();
            start = pageNum - leftLength;
            if (start < 1) {
                start = 1;
            }
            Integer hasLength = pageNum - start + 1;
            end = pageNum + (pageLength - hasLength);
            if (end > pageTotal) {
                end = pageTotal;
                start = pageTotal - pageLength + 1;
            }
        }
        List<Map<String, Object>> pageList = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("num", i);
            map.put("url", buildUrl(i));
            map.put("act", pageNum == i ? true : false);
            pageList.add(map);
        }
        return pageList;
    }

    private String buildUrl(Integer pageNum) {
        return this.queryUrl + this.pageName + "=" + Integer.toString(pageNum);
    }
}
