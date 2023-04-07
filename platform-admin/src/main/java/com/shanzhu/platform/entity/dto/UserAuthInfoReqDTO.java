package com.shanzhu.platform.entity.dto;


import lombok.Data;

@Data
public class UserAuthInfoReqDTO {

    private Integer type;

    private String account;

    private String password;



}
