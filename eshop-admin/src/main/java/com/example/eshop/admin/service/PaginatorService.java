package com.example.eshop.admin.service;

import java.util.Map;

public interface PaginatorService {
    void setPageSize(Integer pageSize);
    void setPageLength(Integer pageLength);
    void setPageName(String pageName);
    Map<String, Object> paging(Integer total);
}
