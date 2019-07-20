package com.lingsi.unp.utils.http;

import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.Map;

/**
 * @description: 广告位招租
 * @author: Wuzu
 * @create: 2019-03-30 14:51
 **/
public class HttpClientUtil {

    private static Logger logger = LogManager.getLogger();

    public static String doGet(String url, Map map) throws Exception{
        logger.info(url);

        StringBuffer paramUrl = new StringBuffer( url );

        int paramCount = 0;
        Iterator it = map.keySet().iterator();
        while(it.hasNext()){
            String key = it.next().toString();
            String value = map.get(key).toString();
            if (paramCount == 0){
                paramUrl.append("?").append(key).append("=").append(value);
            }else {
                paramUrl.append("&").append(key).append("=").append(value);
            }
            paramCount ++;
        }

        org.apache.http.client.HttpClient client = HcbManager.getHcb().build();
        Header[] headers = HttpHeader.custom().build();
        HttpConfig config = HttpConfig.custom().headers(headers).url(paramUrl.toString()).encoding(HttpConsts.CHARSET).client(client);
        // 发送GET请求
        String response = HttpUtil.get(config);
//        logger.info(response);
//        JSONObject respJson = JSON.parseObject(response);
//
//        JSON.parseObject(respJson.getJSONObject("data").toString(), Object.class);
        return response;
    }



    public static String doGetAccept(String url, Map map) throws Exception{
        logger.info(url);

        StringBuffer paramUrl = new StringBuffer( url );

        int paramCount = 0;
        Iterator it = map.keySet().iterator();
        while(it.hasNext()){
            String key = it.next().toString();
            String value = map.get(key).toString();
            if (paramCount == 0){
                paramUrl.append("?").append(key).append("=").append(value);
            }else {
                paramUrl.append("&").append(key).append("=").append(value);
            }
            paramCount ++;
        }

        org.apache.http.client.HttpClient client = HcbManager.getHcb().build();
        Header[] headers = HttpHeader.custom().accept(HttpHeader.Headers.APPLICATION_JSON).build();
        HttpConfig config = HttpConfig.custom().headers(headers).url(paramUrl.toString()).encoding(HttpConsts.CHARSET).client(client);
        // 发送GET请求
        String response = HttpUtil.get(config);
//        logger.info(response);
//        JSONObject respJson = JSON.parseObject(response);
//
//        JSON.parseObject(respJson.getJSONObject("data").toString(), Object.class);
        return response;
    }


    public static String doPostAcceptContentType(String url, Object object, Map header) throws Exception{
        logger.info(url);

        org.apache.http.client.HttpClient client = HcbManager.getHcb().build();
        Header[] headers = null;
        if (header != null){
            headers = HttpHeader.custom().accept(HttpHeader.Headers.APPLICATION_JSON).contentType(HttpHeader.Headers.APPLICATION_JSON).other(header).build();
        }else {
            headers = HttpHeader.custom().accept(HttpHeader.Headers.APPLICATION_JSON).contentType(HttpHeader.Headers.APPLICATION_JSON).build();
        }
        HttpConfig config = HttpConfig.custom().headers(headers).url(url).json(JSON.toJSONString(object)).encoding(HttpConsts.CHARSET).client(client);
        // 发送GET请求
        String response = HttpUtil.post(config);
        logger.info(response);
//        JSONObject respJson = JSON.parseObject(response);
//
//        JSON.parseObject(respJson.getJSONObject("data").toString(), Object.class);
        return response;
    }

    public static String doPostContentType(String url, Object object, Map header) throws Exception{
        logger.info(url);

        org.apache.http.client.HttpClient client = HcbManager.getHcb().build();
        Header[] headers = null;
        if (header != null){
            headers = HttpHeader.custom().contentType(HttpHeader.Headers.APPLICATION_JSON).other(header).build();
        }
        else {
            headers = HttpHeader.custom().contentType(HttpHeader.Headers.APPLICATION_JSON).build();
        }
        HttpConfig config = HttpConfig.custom().headers(headers).url(url).json(JSON.toJSONString(object)).encoding(HttpConsts.CHARSET).client(client);
        // 发送GET请求
        String response = HttpUtil.post(config);
        logger.info(response);
//        JSONObject respJson = JSON.parseObject(response);
//
//        JSON.parseObject(respJson.getJSONObject("data").toString(), Object.class);
        return response;
    }
}
