package com.lingsi.unp.controller.apply;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lingsi.unp.utils.app.parse.JsonSimpleUtil;
import com.lingsi.unp.utils.http.HttpClientUtil;
import com.lingsi.unp.utils.io.http.HttpRequest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestClient {

    static String urlQuery ="http://localhost:8080/notarial/query";
    public static void test() throws Exception {


        Map header = new HashMap();
        header.put("Authorization",String.valueOf(System.currentTimeMillis()));//暂未启用，随便填

        String applySerialno="2019042500000000000000000000000000000003";
        // 发送GET请求
        String response = HttpRequest.doGet(urlQuery,"applySerialno="+applySerialno);
        System.out.println("AuthenticContractSignRessp="+response);
       /* JSONObject jsonObject= JsonSimpleUtil.toJSONObject(response);
        jsonObject.keySet();
        if("200".equals(jsonObject.get("status") )){
            if("messageCode".equals(jsonObject.get("messageCode"))){
                JSONArray fileList = jsonObject.getJSONArray("jsonObject");//文件名称列表
                Iterator file = fileList.iterator();
                while(file.hasNext()){
                    JSONObject fileObject = file.next();
                }
            }
        }*/
    }

    public static void main(String[] args) throws Exception {
        test();
    }
}
