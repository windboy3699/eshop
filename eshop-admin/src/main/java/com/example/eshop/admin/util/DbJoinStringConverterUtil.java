package com.example.eshop.admin.util;

import org.apache.commons.lang.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DbJoinStringConverterUtil implements AttributeConverter<List<Integer>, String> {
    @Override
    public String convertToDatabaseColumn(List<Integer> list) {
        return StringUtils.join(list, "_");
    }

    @Override
    public List<Integer> convertToEntityAttribute(String info) {
        List<Integer> list = new ArrayList<>();
        if (info.length() != 0) {
            List<String> splitString = Arrays.asList(info.split("_"));
            for (String str : splitString) {
                list.add(Integer.valueOf(str));
            }
        }
        return list;
    }
}
