package com.mj.model.request;

import lombok.Data;

@Data
public class UserRequest {

    private Integer id;
    private Integer page;
    private Integer size;
    private String nickName;
    private String ids;
}
