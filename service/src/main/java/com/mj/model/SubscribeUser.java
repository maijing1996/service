package com.mj.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;

/**
 * Created by mj on 2018-05-12.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeUser {

    @Transient
    private Integer id;

    private String nickName;

    private String avatarUrl;

    private String gender;

    @Transient
    private Integer subscribe;

}
