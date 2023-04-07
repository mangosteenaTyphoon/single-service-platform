package com.shanzhu.platform.entity.dto;


import lombok.Data;

@Data
public class IdAndPageReqDTO {

    private String id;

    private long current;

    private long limit;
}
