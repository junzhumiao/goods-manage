package com.qhx.common.util;

import com.aliyun.oss.OSS;
import com.qhx.common.exception.system.SystemException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 上传阿里云文件工具类
 *
 * @author: jzm
 * @date: 2024-03-18 15:27
 **/

@Slf4j
public class UploadFIleUtil
{
    public static String  uploadFile(String prefix, String fileName, String type, File file){
        try
        {
            return uploadFile(prefix,fileName,type,new FileInputStream(file));
        } catch (FileNotFoundException e)
        {
            log.error("文件上传出现异常:",e.getMessage());
            throw new SystemException(e.getMessage());
        }
    }

    // 上传文件
    public static String   uploadFile(String prefix, String fileName, String type, InputStream is)
    {
        OSS client = OSSUtil.getOssClient();
        String fileUrl = OSSUtil.uploadFile(client,prefix + getFilePath(fileName, type),is);
        return fileUrl;
    }


    /**
     * @param name  文件名
     * @param type 文件类型
     * @return 2004/8/18/123123123/qhx.png
     */
    public static  String getFilePath(String name,String type){
        StringBuilder sb = new StringBuilder();
        sb.append(getDatePath());
        sb.append(name);
        sb.append(".");
        sb.append(type);
        return sb.toString();
    }


    /**
     * @return 2004/8/18/
     */
    public  static String getDatePath(){
        StringBuilder sb = new StringBuilder();
        sb.append(DateUtil.getYear());
        sb.append("/");
        sb.append(DateUtil.getMonth());
        sb.append("/");
        sb.append(DateUtil.getDay());
        sb.append("/");
        return sb.toString();
    }

}
