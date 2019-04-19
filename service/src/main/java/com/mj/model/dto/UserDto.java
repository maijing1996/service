package com.mj.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;

    private String openid;

    private String username;

    private String nickName;

    private String password;

    private Integer state;

    private Integer role;

    private String mobile;

    private String qq;

    private String avatarUrl;

    private String gender;

    public void setGender(String gender) {
        if (gender.equals("1")){
            this.gender = "男";
        }else {
            this.gender = "女";
        }
    }
}
