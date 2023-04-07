package com.shanzhu.platform.entity.dto;


import com.shanzhu.platform.entity.News;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NewsContentReqDTO extends News {

    @ApiModelProperty("文章内容")
    private String newsContent;


}
