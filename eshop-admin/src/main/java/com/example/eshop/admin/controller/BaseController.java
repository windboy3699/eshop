package com.example.eshop.admin.controller;

import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    @Resource
    private void configureThymeleafStaticVars(ThymeleafViewResolver viewResolver) {
        if(viewResolver != null) {
            Map<String, Object> vars = new HashMap<>();
            vars.put("staticBaseUrl", "/static");
            viewResolver.setStaticVariables(vars);
        }
    }

    protected String buildAlertScript(String msg, String url) {
        String script = "\ndocument.domain=\"localhost\";\n";
        script += "alert(\"" + msg + "\");\n";
        script += url != "" ? "location.href=\"" + url + "\";" : "history.go(-1);\n";
        return script;
    }
}
