package com.qhx.common.util.ip;

import cn.hutool.core.net.Ipv4Util;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.qhx.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: jzm
 * @date: 2024-03-02 14:24
 **/

@Slf4j
public class AddressUtil
{
    public static final String IP_URL = "https://whois.pconline.com.cn/ipJson.jsp?json=true&ip=";
    // 未知地址
    public static final String UNKNOWN = "XX XX";

    private static final String LOCAL_IP = "0:0:0:0:0:0:0:1";


    /**
     * 通过IP获取真实ip地址
     * @param ip
     * @return 真实地址
     */
    public static String getRealAddressByIP(String ip)
    {
        if(Ipv4Util.isInnerIP(ip))
        {
            return "内网IP";
        }

        JSONObject resObj = getRealAdd(ip);
        if(StringUtil.isEmpty( resObj)){
            return UNKNOWN;
        }
        return resObj.getStr("ip");
    }


    public static JSONObject getRealAdd(String ip)
    {
        HttpRequest request = HttpUtil.createGet(IP_URL + ip)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                        "(KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 Edg/122.0.0.0")
                .header(Header.CONNECTION, "Keep-Alive")
                .header(Header.ACCEPT, "*/*");

        String body = request.execute().body();
        // 解析body
        try
        {
            return JSONUtil.parseObj(body);
        }catch (Exception e){
            log.error("获取地理位置失败: {}",ip);
            return null;
        }
    }

    public static String getRealLocation(String ip){
        if(Ipv4Util.isInnerIP(ip))
        {
            return "内网地址";
        }
        JSONObject resObj = getRealAdd(ip);
        if(StringUtil.isEmpty(resObj))
        {
            return "地址未知";
        }
        return resObj.getStr("pro") + resObj.getStr("city");
    }



}
