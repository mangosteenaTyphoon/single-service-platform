package com.shanzhu.platform.controller;

import com.shanzhu.platform.result.R;
import com.shanzhu.platform.service.MsmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msm")
@Api(tags = {"短信管理"}, description = "腾讯短信管理")
public class MsmApiController {


    @Autowired
    private MsmService msmService;

    //发送登录短信验证码
    @ApiOperation(value = "发送登录短信")
    @GetMapping(value = "/login/{phone}")
    public R code(@PathVariable String phone) {
      return msmService.send(phone,"LOGIN")?R.ok().message("发送成功~"):R.error().message("发送失败~");
    }

    //发送注册短信验证码
    @ApiOperation(value = "发送注册短信")
    @GetMapping(value = "/register/{phone}")
    public R codeRegister(@PathVariable String phone) {
        return msmService.send(phone, "REGISTER")?R.ok().message("发送成功~"):R.error().message("发送失败~");
    }


}
