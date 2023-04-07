package com.shanzhu.platform.entity.dto;

import lombok.Data;

@Data
public class UserAuthInfoByPhoneReqDTO {

    private String phone;

    private String code;
}
