package com.qhx.common.util;

import java.lang.management.ManagementFactory;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: jzm
 * @date: 2024-03-05 08:20
 **/

public class DateUtil extends cn.hutool.core.date.DateUtil
{

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";
    public static String YYYY__MM = "yyyy/MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";


    /**
     * 将秒解析为 00 : 00 格式的字符串
     * @param s
     * @return
     */
    public static String parseMinS(long s)
    {
        String min = s / 60  + "";
        min = min.length() == 1 ? "0" + min : min;
        String second = s % 60 + "";
        second = second.length() == 1 ? "0" + second : second;
       return StringUtil.join(":",min,second);
    }


    public static int getYear(){
        return getYear(new Date());
    }

    public static int getMonth()
    {
        return getMonth(new Date()) ;
    }

    public static int getDay(){
        return getDay(new Date()) ;
    }

    public static int getYear(Date date)
    {
        return dateConvertCalendar(date).get(Calendar.YEAR) ;
    }

    public static int getMonth(Date date)
    {
        return dateConvertCalendar(date).get(Calendar.MONTH) + 1 ;
    }
    public static int getDay(Date date){
        return dateConvertCalendar(date).get(Calendar.DATE) ;
    }


    private static Calendar  dateConvertCalendar(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }


    public static Date getNowDate()
    {
        return new Date();
    }

    public  static int  compare(LocalDateTime date1, LocalDateTime date2){
        long timeStamp1 = getTimeStamp(date1);
        long timeStamp2 = getTimeStamp(date2);
        if(timeStamp1 == timeStamp2){
            return 0;
        }else if(timeStamp1 > timeStamp2){
            return 1;
        }else{
            return -1;
        }
    }

    private static long getTimeStamp(LocalDateTime dateTime){
        Instant instant = dateTime.atZone(ZoneOffset.UTC).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * 计算时间差
     *
     * @param endDate 最后时间
     * @param startTime 开始时间
     * @return 时间差（天/小时/分钟）
     */
    public static String timeDistance(Date endDate, Date startTime)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startTime.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }


}
