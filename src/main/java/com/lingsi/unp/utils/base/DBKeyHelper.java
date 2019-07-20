package com.lingsi.unp.utils.base;

import java.util.UUID;

/**
 * @description: 广告位招租
 * @author: Wuzu
 * @create: 2019-04-03 13:53
 **/
public class DBKeyHelper {

    public static String getKey(){
        String uuid = UUID.randomUUID().toString();
        String today = DateHelper.currentDate();
        return today.replaceAll("/", "") + uuid.replaceAll("-", "") ;
    }

    public static void main(String[] args){
        System.out.println(DBKeyHelper.getKey());
    }
}
