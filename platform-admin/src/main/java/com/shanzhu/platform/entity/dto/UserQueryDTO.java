package com.shanzhu.platform.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserQueryDTO {

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("账号")
    private String userName;

    @ApiModelProperty(value = "查询开始时间",example = "2022-01-12 8:12:22")
    private String startDate;

    @ApiModelProperty(value = "查询结束时间",example = "2022-01-13 8:12:22")
    private String endDate;

}
