package com.shanzhu.platform.utils;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MsmConstantUtils implements InitializingBean{

    /** 腾讯云账户密钥对secretKey（在访问管理中配置） */
    @Value("${tencent.sms.secretId}")
    private String secretID ;
    /** 腾讯云账户密钥对secretKey（在访问管理中配置） */
    @Value("${tencent.sms.keysecret}")
    private String secretKey ;

    @Value("${tencent.sms.smsSdkAppId}")
    private String smsSdkAppID ;

    @Value("${tencent.sms.signName}")
    private String signName ;

    @Value("${tencent.sms.templateId.login}")
    private String templateLoginID ;

    @Value("${tencent.sms.templateId.reset}")
    private String templateResetID ;

    @Value("${tencent.sms.templateId.register}")
    private String templateRegisterID ;

    public static String SECRET_ID;
    public static String SECRET_KEY;
    public static String SMSSDKAPP_ID;
    public static String SIGN_NAME;
    public static String TEMPLATELOGIN_ID;
    public static String TEMPLATERESET_ID;
    public static String TEMPLATEREGISTER_ID;


    @Override
    public void afterPropertiesSet() throws Exception {
        SECRET_ID = secretID;
        SECRET_KEY = secretKey;
        SMSSDKAPP_ID = smsSdkAppID;
        SIGN_NAME = signName;
        TEMPLATELOGIN_ID = templateLoginID;
        TEMPLATERESET_ID=templateResetID;
        TEMPLATEREGISTER_ID=templateRegisterID;

    }


}
