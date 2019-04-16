package com.mj.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mj on 2018-03-04
 */
@Entity
@Data
@Builder
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
//@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","goods","users"})
public class User extends SubscribeUser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonIgnore
    @Column(name = "openid")
    private String openid;

    @Column(name = "username", unique = true, length = 50)
    @JsonProperty("username")
    private String username;

    @Column(name = "nick_name")
    @JsonProperty("nickName")
    private String nickName;

    @Column(name = "password", length = 200)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "state", length = 10)
    @JsonProperty("state")
    private Integer state;

    @Column(name = "role", length = 10)
    @JsonProperty("role")
    private Integer role;

    @Column(name = "mobile", length = 50)
    @JsonProperty("mobile")
    private String mobile;

    @Column(name = "qq", length = 50)
    @JsonProperty("qq")
    private String qq;

    @Column(name = "avatar_url", length = 200)
    @JsonProperty("avatarUrl")
    private String avatarUrl;

    @Column(name = "gender", length = 10)
    @JsonProperty("gender")
    private String gender;

    @Transient
    private Set<Goods> goods = new HashSet<>();

    @Transient
    private Set<User> users = new HashSet<>();

}
