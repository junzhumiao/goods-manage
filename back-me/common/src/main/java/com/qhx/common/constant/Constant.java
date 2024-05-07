package com.qhx.common.constant;


/**
 * 常量
 *
 * @author: jzm
 * @date: 2024-02-26 21:40
 **/

public class Constant
{


    /**
     * 响应成功、失败标识
     */
    public static final int OK_CODE = 200;
    public static final int ERROR_CODE = 400;
    public static final int WARN_CODE = 300;
    public static final String OK_MES = "ok";
    public static final String FAIL_MES = "fail";

    public static String UTF_8 = "utf-8";


    /**
     * 密码错误允许最大次数
     */
    public static final Integer PWD_ERR_MAX_CNT = 5;

    /**
     * token自定义字段
     */
    public static String USER_ID = "userId";
    public static String PASSWORD = "password";

    /**
     * 产品相关
     */
    public static final int CATEGORY_NAME_MIN_LEN = 1;
    public static final int CATEGORY_NAME_MAX_LEN = 5;


    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer";


    /**
     *  前缀
     */
    public static final String HTTPS_PREFIX = "https://";

    public static final String HTTP_PREFIX = "http://";

    public static int In_Code_Pre_Len = 6; // 就是随机的6位数字。




    /**
     * 自动识别json对象白名单配置（仅允许解析的包名，范围越小越安全）
     */
    public static final String[] JSON_WHITELIST_STR = { "org.springframework", "com.qhx" };

    // 角色id
    public static final Integer Role_Admin = 0;
    public static final Integer Role_Farmer = 1;
    // 订单状态: 待支付,未发货,已发货,已完成
    public static final String Order_UnPaid = "0";
    public static final String Order_UnShipped = "1";
    public static final String Order_Shipped = "2";
    public static final String Order_Done = "3";
    // 通用status判断
    public static final CharSequence Enable = "0";
    public static final CharSequence Disable = "1";
    // 商品默认图片:适用于,商品没上传图片,但是需要一张默认图片展示(插入这个)
    public static final CharSequence DefGoodsImg = "https://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/goods/default.png";
}
