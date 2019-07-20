package com.lingsi.unp.utils.io.file;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MapTools {

    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String,Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value =  field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }
}
