package com.shanzhu.platform.service.impl;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.shanzhu.platform.service.OssService;
import com.shanzhu.platform.utils.ConstantPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
/*
这里只写了上传文件 删除文件是没有必要的操作
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        System.out.println(file.getName());
        String endPoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;


        try {
            //创建OSS实例
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            //获取唯一的值
            String uuid= UUID.randomUUID().toString().replaceAll("-","");
            //得到随机的文件名
            String fileName=file.getOriginalFilename()+uuid;

            //得到加上文件路径的文件名
            //根据时间来将文件分类
            String datePath=new DateTime().toString("yyyy/MM/dd");

            fileName =datePath+"/"+fileName;
            //调用oss方法实现上传
            //第一个参数 Bucket名称  第二个参数 oss文件路径和文件名称  第三个参数是 上传文件输入流
            ossClient.putObject(bucketName,fileName,inputStream);

            //关闭
            ossClient.shutdown();
            //获取url地址
            String url = "https://" + bucketName + "." + endPoint + "/" + fileName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

}
