package com.lingsi.unp.utils.app.parse;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.lingsi.unp.model.auth.AppUser;

/**
 * JSON生成工具
 */
public class JsonSimpleUtil {

    public static JSONObject toJSONObject(String str){
        return JSONObject.parseObject(str);
    }
    public static Object toJavaObject(JSONObject jsonObject,Class clazz){
        return JSONObject.toJavaObject(jsonObject, clazz);
    }

    public static JSONObject toJSONObject(Object javaObject){
        return (JSONObject)JSONObject.toJSON(javaObject);
    }

    public static JSONObject combineJson(JSONObject srcObj, JSONObject addObj) throws JSONException {
        if(addObj==null || srcObj==null)
            return null;

        for(String key: addObj.keySet()){
            if(srcObj.containsKey(key)){
                continue;
            }
            srcObj.put(key, addObj.get(key));
        }
        return srcObj;
    }
    public static void main(String[] args){
        AppUser appUser = new AppUser();
        appUser.setUsername("孙涛");
        JSONObject js = (JSONObject)JSONObject.toJSON(appUser);
        System.out.println(js);
    }
}
