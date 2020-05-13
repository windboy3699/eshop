package com.example.eshop.admin.service.impl;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class PaginatorServiceImpl {
    private Integer pageNum = null; //第几页
    private Integer pageSize = 10; //每页条目数量
    private Integer pageWidth = 5; //显示的页码宽度(多少个)
    private String pageName = "page"; //页码参数名

    public PaginatorServiceImpl() {super();}

    public PaginatorServiceImpl(Integer pageSize, Integer pageWidth, String pageName) {
        this.pageSize = pageSize;
        this.pageWidth = pageWidth;
        this.pageName = pageName;
    }

    public Integer getPageSize() { return pageSize; }

    /**
     * 获取当前是第几页
     * @return
     */
    public Integer getPageNum() {
        if (pageNum != null) {
            return pageNum;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
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
        return pageNum;
    }

    private String getQueryUrl() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        StringBuffer url = request.getRequestURL();
        String queryString = request.getQueryString();

        List<String> queryStringList = new ArrayList<>();
        if (queryString != null && queryString.length() != 0) {
            String[] queryStringArray = queryString.split("&");
            queryStringList = new ArrayList<>(queryStringArray.length);
            Collections.addAll(queryStringList, queryStringArray);
        }
        String queryUrl;
        if (queryStringList == null || queryStringList.isEmpty()) {
            queryUrl = url + "?";
        } else {
            String removeItem = null;
            for (String item : queryStringList) {
                if (item.indexOf(pageName+"=") != -1) {
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
        return queryUrl;
    }

    public Map<String, Object> paging(long total) {
        Integer pageNum = getPageNum();
        Integer pageTotal = (new Double(Math.ceil(total/pageSize))).intValue();
        String queryUrl = getQueryUrl();

        Map<String, Object> map = new HashMap<>();

        map.put("total", total);

        String firstPageUrl = pageNum == 1 ? "" : buildUrl(queryUrl,1);
        map.put("firstPageUrl", firstPageUrl);

        String prevPageUrl = pageNum == 1 ? "" : buildUrl(queryUrl,pageNum - 1);
        map.put("prevPageUrl", prevPageUrl);

        String nextPageUrl = pageNum == pageTotal ? "" : buildUrl(queryUrl,pageNum + 1);
        map.put("nextPageUrl", nextPageUrl);

        String lastPageUrl = pageNum == pageTotal ? "" : buildUrl(queryUrl, pageTotal);
        map.put("lastPageUrl", lastPageUrl);

        map.put("middlePageUrl", middlePageUrl(pageNum, pageTotal, queryUrl));

        return map;
    }

    private List<Map<String, Object>> middlePageUrl(Integer pageNum, Integer pageTotal, String queryUrl) {
        Integer start,end;
        if (pageTotal <= pageWidth) {
            start = 1;
            end = pageTotal;
        } else {
            double doubleLeftLength = Math.floor((pageWidth-1)/2);
            Integer leftLength = (new Double(doubleLeftLength)).intValue();
            start = pageNum - leftLength;
            if (start < 1) {
                start = 1;
            }
            Integer hasLength = pageNum - start + 1;
            end = pageNum + (pageWidth - hasLength);
            if (end > pageTotal) {
                end = pageTotal;
                start = pageTotal - pageWidth + 1;
            }
        }
        List<Map<String, Object>> pageList = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("num", i);
            map.put("url", buildUrl(queryUrl, i));
            map.put("act", pageNum == i ? true : false);
            pageList.add(map);
        }
        return pageList;
    }

    private String buildUrl(String queryUrl, Integer pageNum) {
        return queryUrl + pageName + "=" + Integer.toString(pageNum);
    }
}
