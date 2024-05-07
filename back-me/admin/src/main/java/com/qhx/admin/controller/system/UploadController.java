package com.qhx.admin.controller.system;

import com.qhx.common.controller.BaseUploadController;
import com.qhx.common.model.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传控制器
 *
 * @author: jzm
 * @date: 2024-03-03 11:05
 **/
@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController extends BaseUploadController
{
    private static final String[] imgTypes = new String[]{"jpg","png"};

    private static final String AVATAR_PATH_prefix = "img/avatar/";
    private static final String GoodsImg_PATH_prefix = "img/goods/";

    @RequestMapping(value = "/avatar",method = RequestMethod.POST)
    public AjaxResult uploadAvatar(@RequestParam MultipartFile file){
        return uploadImg(AVATAR_PATH_prefix,MAX_FILE_SIZE,imgTypes,file);
    }

    @RequestMapping(value = "/goods/img",method = RequestMethod.POST)
    public AjaxResult uploadGoodsImg(@RequestParam MultipartFile[] file){
        return uploadImg(GoodsImg_PATH_prefix,MAX_FILE_SIZE,imgTypes,file);
    }


}
