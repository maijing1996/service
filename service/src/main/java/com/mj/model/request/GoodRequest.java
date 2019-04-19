package com.mj.model.request;

import lombok.Data;

@Data
public class GoodRequest {

    private Integer id;
    private String ids;
    private Integer page;
    private Integer size;
    private String goodName;
}
