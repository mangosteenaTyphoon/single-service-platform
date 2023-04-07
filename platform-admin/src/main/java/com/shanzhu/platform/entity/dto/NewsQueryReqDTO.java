package com.shanzhu.platform.entity.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NewsQueryReqDTO {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "摘要")
    private String summary;

    @ApiModelProperty(value = "用户昵称")
    private String userName;

    @ApiModelProperty(value = "当前页")
    private long current;

    @ApiModelProperty(value = "每页的数量")
    private long limit;

    @ApiModelProperty(value = "状态")
    private Integer status;








}
