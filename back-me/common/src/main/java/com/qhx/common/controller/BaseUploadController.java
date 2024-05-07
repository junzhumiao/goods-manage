package com.qhx.common.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.qhx.common.exception.system.SystemException;
import com.qhx.common.model.AjaxResult;
import com.qhx.common.util.StringUtil;
import com.qhx.common.util.UploadFIleUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: jzm
 * @date: 2024-03-18 15:29
 **/

@Slf4j
public class BaseUploadController
{
    protected static final Long KB = 1024L;
    protected static final Long MAX_FILE_SIZE = 800 * KB;

    public AjaxResult uploadImg(String prefix, Long singleFileSize, String[] limitedTypes ,MultipartFile...files){
        return uploadFile("imgUrls",prefix,singleFileSize,limitedTypes,files);
    }

    public AjaxResult uploadFile(String resField,String prefix, Long singleFileSize, String[] limitedTypes , MultipartFile... files)
    {
        List<String> imgUrls = new ArrayList<>();
        for (MultipartFile file : files)
        {
            // 获得输入输出流
            InputStream is ;
            try
            {
                is = file.getInputStream();
            } catch (IOException e)
            {
                log.error("文件上传出现异常:",e.getMessage());
                throw new SystemException("文件不存在");
            }

            String type = FileUtil.extName(file.getOriginalFilename()); // 原始文件名获取文件类型
            if(!StringUtil.equalsAnyIgnoreCase(type,limitedTypes)){
                return AjaxResult.error("文件上传只能是jpg、png格式");
            }
            if( file.getSize() > singleFileSize)
            {
                return AjaxResult.error("文件大小必须为800 KB以内");
            }
            String fileName = IdUtil.simpleUUID();
            String imgUrl = UploadFIleUtil.uploadFile(prefix,fileName, type, is);
            if(StringUtil.isEmpty(imgUrl))
            {
                throw new SystemException("文件上云失败!");
            }
            imgUrls.add(imgUrl);
        }
        HashMap<String, Object> resMap = new HashMap<>();
        resMap.put(resField,imgUrls); // 上传文件,返回图片列表。
        return AjaxResult.success(resMap);
    }

}
