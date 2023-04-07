package com.shanzhu.platform.entity.dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ColumnInfoReqDTO {

    @ApiModelProperty(value = " 主键")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "专栏名称")
    private String columnName;

    @ApiModelProperty(value = "专栏介绍")
    private String columnDescribe;
}
