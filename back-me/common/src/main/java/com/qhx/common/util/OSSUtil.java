package com.qhx.common.util;

import com.aliyun.oss.*;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * 阿里OSS镜像服务工具类
 *
 * @author: jzm
 * @date: 2024-03-06 21:18
 **/

@Slf4j
public class OSSUtil
{
    private static final String endpoint = "XXX";
    private static final String bucketName = "XXX";
    private static final String accessKeyId = "XXX";
    private static final String secretAccessKey = "XXX";

    private static OSS ossClient = null;

    public static String getBucketName()
    {
        return bucketName;
    }

    public static String getEndpoint()
    {
        return endpoint;
    }


    // 也对,我们创建出,客户端连接
    // 然后操作资源是不确定的
    // 每次创建,接着释放显然浪费资源
    // 因此先创建连接客户端,操作，边传客户端边操作即可

    /**
     * 上传文件
     *
     * @param ossClient oss连接
     * @param ossPath   上传文件相对路径+文件名如"upload/2023/01/11/cake.jpg"
     * @param file      上传File
     * @return 文件的上传的网络地址
     */
    public static String uploadFile(OSS ossClient, String ossPath, File file)
    {
        try
        {
            return uploadFile(ossClient, ossPath, new FileInputStream(file));
        } catch (FileNotFoundException e)
        {
            log.error("上传文件io异常:" + e.getMessage());
            return null;
        }
    }

    public static String uploadFile(OSS ossClient, String ossPath, InputStream is)
    {
        try
        {
            // 创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            // 指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            // 指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            // 指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            // 文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            PutObjectResult putResult = ossClient.putObject(bucketName, ossPath, is, metadata);
            // 解析结果
            String resultStr = putResult.getETag();
            if (StringUtil.isNull(resultStr))
            {
                return null;
            }
            return getUrl(ossClient, bucketName, ossPath);
        } catch (Exception e)
        {
            e.printStackTrace();
            log.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
            return null;
        }

    }


    /**
     * @param ossClient
     * @param url       文件的网络地址
     */
    public static void deleteObject(OSS ossClient, String url)
    {
        String tempStr = "";
        if (url.contains("?"))
        { // 有沒有其他後綴
            String[] splits = url.split("/?");
            tempStr = splits[0];
        } else
        {
            tempStr = url;
        }
        String domain = getOSSDomain();
        String[] split = tempStr.split(domain);
        String key = split[split.length - 1];
        deleteObject(ossClient, bucketName, key);
    }

    // 获取OSS域名
    public static String getOSSDomain()
    {
        return getBucketName() + "." + getEndpoint() + "/";
    }


    /**
     * 根据key删除OSS服务器上的文件
     *
     * @param ossClient  oss连接
     * @param bucketName 存储空间
     * @param key        Bucket下的文件的路径名+文件名 如："upload/2023/01/11/cake.jpg"
     */
    public static void deleteObject(OSS ossClient, String bucketName, String key)
    {
        ossClient.deleteObject(bucketName, key);
        log.info("删除" + bucketName + "下的文件" + key + "成功");
    }

    /**
     * 获取上传文件url
     *
     * @param ossClient
     * @param bucketName
     * @param key
     * @return
     */
    private static String getUrl(OSS ossClient, String bucketName, String key)
    {
        //设置URl过期时间为99年：3600L*1000*24*365*99
        // 1711799937
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 99);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key);
        generatePresignedUrlRequest.setExpiration(expiration);
        URL url = ossClient.generatePresignedUrl(generatePresignedUrlRequest);
        String returnUrl = url.toString();
        return returnUrl;
    }


    public static OSSObject getObject(OSS ossClient, String key)
    {
        OSSObject object = null;
        try
        {
            object = ossClient.getObject(bucketName, key);
            //文件大小
            long fileSize = object.getObjectMetadata().getContentLength();
            //文件相对路径
            String ossPath = object.getKey();
            //文件输入流
            InputStream is = object.getObjectContent();
            log.info("success to getObject,fileSize:" + fileSize + "\nossPath:" + ossPath + "\ninputStream:" + is);
        } catch (OSSException e)
        {
            e.printStackTrace();
        } catch (ClientException e)
        {
            e.printStackTrace();
        }
        return object;
    }


    public static OSS getOssClient()
    {
        return getOssClient(endpoint, accessKeyId, secretAccessKey);
    }

    /**
     * 获得阿里云OSS客户端对象
     * 备注：阿里云OSS SDK中提供了自动重连的功能，以确保应用程序在遇到网络问题时仍能与OSS有效通信。
     *
     * @param endpoint
     * @param accessId
     * @param accessKey
     * @return
     */
    public static OSS getOssClient(String endpoint, String accessId, String accessKey)
    {
        if (ossClient == null)
        {
            ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
            // 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
            conf.setMaxConnections(500);
            // 设置Socket层传输数据的超时时间，默认为50000毫秒。
            conf.setSocketTimeout(10000);
            // 设置建立连接的超时时间，默认为50000毫秒。
            conf.setConnectionTimeout(10000);
            // 设置从连接池中获取连接的超时时间（单位：毫秒），默认不超时。
            conf.setConnectionRequestTimeout(10000);
            // 设置连接空闲超时时间。超时则关闭连接，默认为60000毫秒。
            conf.setIdleConnectionTime(10000);
            // 设置失败请求重试次数，默认为3次。
            conf.setMaxErrorRetry(5);
            ossClient = new OSSClientBuilder().build(endpoint, accessId, accessKey, conf);
        }
        setCorsOptions(ossClient);
        return ossClient;

    }

    private static void setCorsOptions(OSS ossClient)
    {
        SetBucketCORSRequest request = new SetBucketCORSRequest(bucketName);
        // 每个存储空间最多允许设置10条跨域规则。
        ArrayList<SetBucketCORSRequest.CORSRule> putCorsRules = new ArrayList<SetBucketCORSRequest.CORSRule>();

        SetBucketCORSRequest.CORSRule corRule = new SetBucketCORSRequest.CORSRule();

        ArrayList<String> allowedOrigin = new ArrayList<String>();
        // 指定允许跨域请求的来源。
        allowedOrigin.add("http://localhost:8111");

        ArrayList<String> allowedMethod = new ArrayList<String>();
        // 指定允许的跨域请求方法(GET/PUT/DELETE/POST/HEAD)。
        allowedMethod.add("GET");

        ArrayList<String> allowedHeader = new ArrayList<String>();
        // 是否允许预取指令（OPTIONS）中Access-Control-Request-Headers头中指定的Header。
        allowedHeader.add("x-oss-test");

        ArrayList<String> exposedHeader = new ArrayList<String>();
        // 指定允许用户从应用程序中访问的响应头。
        exposedHeader.add("x-oss-test1");
        // AllowedOrigins和AllowedMethods最多支持一个星号（*）通配符。星号（*）表示允许所有的域来源或者操作。
        corRule.setAllowedMethods(allowedMethod);
        corRule.setAllowedOrigins(allowedOrigin);
        // AllowedHeaders和ExposeHeaders不支持通配符。
        corRule.setAllowedHeaders(allowedHeader);
        corRule.setExposeHeaders(exposedHeader);
        // 指定浏览器对特定资源的预取（OPTIONS）请求返回结果的缓存时间，单位为秒。
        corRule.setMaxAgeSeconds(10);

        // 最多允许10条规则。
        putCorsRules.add(corRule);
        // 已存在的规则将被覆盖。
        request.setCorsRules(putCorsRules);
        // 指定是否返回Vary: Origin头。指定为TRUE，表示不管发送的是否为跨域请求或跨域请求是否成功，均会返回Vary: Origin头。指定为False，表示任何情况下都不会返回Vary: Origin头。
        // request.setResponseVary(Boolean.TRUE);
        ossClient.setBucketCORS(request);
    }


}
