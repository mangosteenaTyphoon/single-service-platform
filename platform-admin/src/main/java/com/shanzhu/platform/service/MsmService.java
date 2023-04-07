package com.shanzhu.platform.service;

import java.util.Map;

public interface MsmService {
    //发送验证码
    public boolean send(String phone,String msmType);

}
