package com.lingsi.unp.utils.base;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: 日期时间工具类
 * @author: Wuzu
 * @create: 2019-03-29 17:11
 **/
public class DateHelper {
    static final SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy/MM/dd");
    static final SimpleDateFormat timeSdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    static final SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy");
    static final SimpleDateFormat monthSdf = new SimpleDateFormat("MM");
    static final SimpleDateFormat daySdf = new SimpleDateFormat("dd");
    static final SimpleDateFormat hourSdf = new SimpleDateFormat("HH");
    static final SimpleDateFormat minuteSdf = new SimpleDateFormat("mm");
    static final String[] NO_CHI = {"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    static final String[] MONTH_CHI = {"十", "二十", "三十"};

    public static String format(String dateStr, String fromFormat, String toFormat) throws Exception{
        SimpleDateFormat fromSdf = new SimpleDateFormat(fromFormat);
        SimpleDateFormat toSdf = new SimpleDateFormat(toFormat);
        return toSdf.format(fromSdf.parse(dateStr));
    }

    public static String currentDate(){
        Date date = new Date();
        return dateSdf.format(date);
    }

    public static String currentTime(){
        Date date = new Date();
        return timeSdf.format(date);
    }
    public static String getYear(){
        Date date = new Date();
        return yearSdf.format(date);
    }

    /**
     * @param date yyyy/MM/dd HH:mm:ss
     * @return
     * @throws Exception
     */
    public static String getYear(String date) throws Exception{
        return yearSdf.format(dateSdf.parse(date));
    }
    /**
     * @param date yyyy/MM/dd HH:mm:ss
     * @return
     * @throws Exception
     */
    public static String getMonth(String date) throws Exception{
        return monthSdf.format(dateSdf.parse(date));
    }
    /**
     * @param date yyyy/MM/dd HH:mm:ss
     * @return
     * @throws Exception
     */
    public static String getDay(String date) throws Exception{
        return daySdf.format(dateSdf.parse(date));
    }
    /**
     * @param date yyyy/MM/dd HH:mm:ss
     * @return
     * @throws Exception
     */
    public static String getHour(String date) throws Exception{
        return hourSdf.format(timeSdf.parse(date));
    }
    /**
     * @param date yyyy/MM/dd HH:mm:ss
     * @return
     * @throws Exception
     */
    public static String getMinute(String date) throws Exception{
        return minuteSdf.format(timeSdf.parse(date));
    }

    public static String getCureentYear(){
        return yearSdf.format(new Date());
    }

    public static String getCureentMonth(){
        return monthSdf.format(new Date());
    }

    public static String getCureentDay(){
        return daySdf.format(new Date());
    }

    public static String getCurrentYearChi(){
        return no2Chi(getCureentYear());
    }

    public static String getCurrentMonthChi(){
        String month = getCureentMonth();
        if (month.startsWith("0")){
            month = month.substring(1, 2);
        }
        String res = no2Chi(month);
        return no2ChiSpecial(month);
    }

    public static String getCurrentDayChi(){
        String day = getCureentDay();
        if (day.startsWith("0")){
            day = day.substring(1, 2);
        }
        return no2ChiSpecial(day);
    }

    public static String getYearChi(String date) throws Exception{
        return no2Chi(yearSdf.format(dateSdf.parse(date)));
    }

    public static String getMonthChi(String date) throws Exception{
        String month = monthSdf.format(dateSdf.parse(date));
        if (month.startsWith("0")){
            month = month.substring(1, 2);
        }
        String res = no2Chi(month);
        return no2ChiSpecial(month);
    }

    public static String getDayChi(String date) throws  Exception{
        String day = daySdf.format(dateSdf.parse(date));
        if (day.startsWith("0")){
            day = day.substring(1, 2);
        }
        return no2ChiSpecial(day);
    }



    public static String no2Chi(String no){
        String result = "";
        char[] nos = no.toCharArray();
        for (char thisNo : nos){
            result += NO_CHI[Integer.parseInt(String.valueOf(thisNo))];
        }
        return result;
    }

    public static String no2ChiSpecial(String no){
        if (no.equals("10"))
        {
            return MONTH_CHI[0];
        }else if (no.equals("20"))
        {
            return MONTH_CHI[1];
        }else if (no.equals("30"))
        {
            return MONTH_CHI[2];
        }


        String result = "";
        char[] nos = no.toCharArray();
        int k = 0;
        int len = no.length();
        for (char thisNo : nos){
            if (len==2&&k==0){
                if ('1' == thisNo){
                    result += MONTH_CHI[0];
                }else if ('2' == thisNo)
                {
                    result += MONTH_CHI[1];
                }else if ('3' == thisNo)
                {
                    result += MONTH_CHI[2];
                }
                k ++;
            }else {
                result += NO_CHI[Integer.parseInt(String.valueOf(thisNo))];
            }
        }

        return result;
    }


    public static void main(String[] args) throws Exception
    {
//        System.out.println(getCureentYear());
//        System.out.println(getCureentMonth());
//        System.out.println(getCureentDay());

        System.out.println(getCurrentDayChi());
        System.out.println(getCurrentMonthChi());
    }

}
