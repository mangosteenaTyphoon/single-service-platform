package com.shanzhu.platform.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
@RequestMapping("utils")
@Api(tags = {"工具管理"}, description = "获取用户ip，归属地，天气等")
public class UtilsController {
    private String IPURL = "http://api.ip138.com/ip/?datatype=jsonp&token=47bc76ba585c1fe3ddcabbbeeb618084";
    private String WEATHERURL="https://api.ip138.com/weather/?type=1&callback=find&token=89fe92a0a83361e02f127da8deb6d71b";
    @GetMapping("getUserIp")
    @ApiOperation("获取用户IP地址")
    @ResponseBody
    public String getUserIP(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(IPURL, String.class);


    }

    @GetMapping("getUserWeather")
    @ApiOperation("获取天气信息")
    public String getUserWeather(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(WEATHERURL, String.class);

    }



}
