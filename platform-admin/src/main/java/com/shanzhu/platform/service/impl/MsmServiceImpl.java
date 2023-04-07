package com.shanzhu.platform.service.impl;

import cn.hutool.core.util.StrUtil;
import com.shanzhu.platform.redis.service.RedisService;
import com.shanzhu.platform.service.MsmService;

import com.shanzhu.platform.utils.MsmConstantUtils;
import com.shanzhu.platform.utils.RandomUtil;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.concurrent.TimeUnit;

/**
 * @author Eric
 * @create  2022-05-22 15:09
 */

@Slf4j
@Service
public class MsmServiceImpl implements MsmService {


    @Autowired
    private RedisService redisService;


    @Override
    public boolean send(String phone,String msmType) {
        String set = phone + msmType;
        //1、从redis中获取验证码，如果获取到就直接返回
        String code = redisService.getCacheObject(set);
        if(!StrUtil.isEmpty(code))
        {
            log.info("验证码已经存在，不需要重复发送");
            return true;
        }

        //2、如果获取不到，就进行阿里云发送
        code = RandomUtil.getFourBitRandom();//生成验证码的随机值

        //调用方法
        boolean isSend = sendPhone(code,phone,msmType);
        if(isSend) {
            //往redis中设置数据：key、value、过期值、过期时间单位  MINUTES代表分钟
            redisService.setCacheObject(set,code,300, TimeUnit.SECONDS);
            return true;
        } else {
            return false;
        }

    }



    public boolean sendPhone(String code, String phone,String msmType){
        //判断手机号是否为空
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential(MsmConstantUtils.SECRET_ID, MsmConstantUtils.SECRET_KEY);
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            // 实例化一个client选项，
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的  第二个参数是地域信息
            SmsClient client = new SmsClient(cred, "ap-nanjing", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();

            //设置发送相关的参数
            String[] phoneNumberSet1 = {"86"+phone};
            req.setPhoneNumberSet(phoneNumberSet1);//发送的手机号
            //设置固定的参数
            req.setSmsSdkAppid(MsmConstantUtils.SMSSDKAPP_ID);// 短信应用ID: 短信SdkAppId在 [短信控制台] 添加应用后生成的实际SdkAppId
            req.setSign(MsmConstantUtils.SIGN_NAME);//短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名
            if(msmType.equals("LOGIN"))  req.setTemplateID(MsmConstantUtils.TEMPLATELOGIN_ID);//模板 ID: 必须填写已审核通过的模板 ID
            if(msmType.equals("RESET"))  req.setTemplateID(MsmConstantUtils.TEMPLATERESET_ID);
            if(msmType.equals("REGISTER"))   req.setTemplateID(MsmConstantUtils.TEMPLATEREGISTER_ID);
            String[] templateParamSet1 = new String[2];
            templateParamSet1[0]=code;//模板的参数 第一个是验证码，第二个是过期时间
            templateParamSet1[1]=String.valueOf(30);
            req.setTemplateParamSet(templateParamSet1);//发送验证码

            //发送短信
            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = client.SendSms(req);
            System.out.println("resp"+resp);
            // 输出json格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(resp));
            return true;
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            return false;
        }

    }
}

