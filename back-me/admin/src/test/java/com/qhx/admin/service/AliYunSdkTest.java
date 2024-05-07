package com.qhx.admin.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.*;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.*;
import com.aliyuncs.exceptions.ClientException;
import com.qhx.common.util.OSSUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

/**
 * @author: jzm
 * @date: 2024-03-06 20:12
 **/

@SpringBootTest
public class AliYunSdkTest
{

    @Test
    public void test() throws ClientException
    {
        OSS oss = OSSUtil.getOssClient();
        String filePath = OSSUtil.uploadFile(oss, "img/qhx.png", new File("C:\\Users\\qhx20\\Pictures\\Sc" +
                "reenshots\\屏幕截图 2023-06-26 110841.png"));

        String url = "https://jzm2004.oss-cn-zhangjiakou.aliyuncs.com/img/test.png";
        OSSUtil.deleteObject(oss,url);
        System.out.println(12);
    }
}
