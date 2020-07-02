package com.example.eshop.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UploadController {
    @RequestMapping(value = "/admin/upload", method = RequestMethod.GET)
    public String show() {
        return "upload";
    }

    @ResponseBody
    @RequestMapping(value = "/admin/upload", method = RequestMethod.POST)
    public String upload() {
        String content = "<script type=\"text/javascript\">document.domain = \"localhost\";";
        content += "parent.uploadCallBack(\"" +  "www.image.com" + "\");";
        content += "</script>";
        return content;
    }
}