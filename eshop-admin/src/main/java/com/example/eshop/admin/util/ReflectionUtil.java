package com.example.eshop.admin.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtil {
    /**
     * source不为空的属性复制给target
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static  <T> T copyNotNullProperties(T source, T target) {
        Class sourceClass = source.getClass();
        Field[] fields = sourceClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), sourceClass);
                Method readMethod = pd.getReadMethod();
                Method writeMethod= pd.getWriteMethod();
                if (readMethod.invoke(source) != null) {
                    writeMethod.invoke(target, readMethod.invoke(source));
                }
            } catch (Exception e) {

            }
        }
        return target;
    }
}
