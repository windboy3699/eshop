package com.example.eshop.admin.controller;

import com.example.eshop.admin.dto.ResponseDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Controller
public class UploadController {
    @Autowired
    private String imageDomainUrl;

    private String imageSavePath = "/Users/xiaodongpan/Learning/static/image";

    private String separator = File.separator;

    private List<String> contentTypeList = new ArrayList<>(Arrays.asList("image/jpeg", "image/png", "image/gif"));

    /**
     * iframe图片上传
     * @return
     */
    @RequestMapping(value = "/admin/upload", method = RequestMethod.GET)
    public String show() {
        return "upload";
    }

    /**
     * iframe图片上传
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/admin/upload", method = RequestMethod.POST)
    public String doUpload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String content = "<script type=\"text/javascript\">document.domain = \"localhost\";";

        if (multipartFile.isEmpty() || StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            content += "alert(\"图片不能为空\");";
            content += "parent.cancelUpload();";
            return content;
        }
        String contentType = multipartFile.getContentType();
        if (!contentTypeList.contains(contentType)) {
            content += "alert(\"图片格式错误\");";
            content += "parent.cancelUpload();";
            return content;
        }
        String datePath = getDatePath();
        String fileName = saveImage(multipartFile, imageSavePath + datePath);
        if (StringUtils.isNotBlank(fileName)) {
            String imageUrl = imageDomainUrl + datePath + separator + fileName;
            content += "parent.uploadCallBack(\"" +  imageUrl + "\");";
            content += "</script>";
            return content;
        }
        content += "alert(\"上传失败\");";
        content += "parent.cancelUpload();";
        return content;
    }

    /**
     * CkEditor图片上传
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/admin/upload/forCkEditor", method = RequestMethod.POST)
    public ResponseDto<Object> uploadForCKEditor(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty() || StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            return ResponseDto.create(10001, "图片不能为空");
        }
        String contentType = multipartFile.getContentType();
        if (!contentTypeList.contains(contentType)) {
            return ResponseDto.create(10002, "图片格式错误");
        }
        String datePath = getDatePath();
        String fileName = saveImage(multipartFile, imageSavePath + datePath);
        if (StringUtils.isNotBlank(fileName)) {
            String imageUrl = imageDomainUrl + datePath + separator + fileName;
            return ResponseDto.create(0, "success", imageUrl);
        }
        return ResponseDto.create(10003, "上传失败");
    }

    private String getDatePath() {
        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        String datePath = separator + year + separator + String.format("%02d", month);;
        return datePath;
    }

    public String saveImage(MultipartFile multipartFile,String path) throws IOException {
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();
        String fileName = System.currentTimeMillis() + ".jpg";
        String fullFileName = path + File.separator + fileName;
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fullFileName));
        byte[] bs = new byte[1024];
        int len;
        while ((len = fileInputStream.read(bs)) != -1) {
            bufferedOutputStream.write(bs, 0, len);
        }
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        return fileName;
    }
}