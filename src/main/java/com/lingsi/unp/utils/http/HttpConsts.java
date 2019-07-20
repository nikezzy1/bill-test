package com.lingsi.unp.utils.http;

public class HttpConsts {

    /** 超时时间*/
    public static int timeout = 10 * 1000;
    /** 连接池总连接数*/
    public static int maxTotal = 100;
    /** 每个路由最大连接数*/
    public static int maxPerRoute = 100;
    /** 重试次数*/
    public static int tryTimes = 2;
    /** 连接拒绝时是否重试*/
    public final static boolean RETRYWHENINTERRUPTEDIO = true;

    public final static String CHARSET = "UTF-8";
}
