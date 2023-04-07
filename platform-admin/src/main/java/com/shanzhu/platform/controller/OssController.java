package com.shanzhu.platform.controller;


import com.shanzhu.platform.result.R;
import com.shanzhu.platform.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@Api(tags = {"文件管理"}, description = "文件上传下载")
@RequestMapping("/file")
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像的方法
    @PostMapping(value = "upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("上传文件")
    public R uploadOssFile(@RequestPart("file")MultipartFile file){
        //获取文件 并上传
        //返回上传的路径
        String url=ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }


}
