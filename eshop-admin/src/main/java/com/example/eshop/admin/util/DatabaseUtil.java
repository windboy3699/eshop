package com.example.eshop.admin.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DatabaseUtil {
    public static  <T> T updateDomain(T domain, T originDoamin) {
        Class domainClass = domain.getClass();
        Field[] fields = domainClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), domainClass);
                Method readMethod = pd.getReadMethod();
                Method writeMethod= pd.getWriteMethod();
                if (readMethod.invoke(domain) != null) {
                    //将old对象属性值设置为new对象的属性值
                    writeMethod.invoke(originDoamin, readMethod.invoke(domain));
                }
            } catch (Exception e){

            }
        }
        return originDoamin;
    }
}
