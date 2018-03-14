package com.gjj.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gjj on 2018-03-04
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty("id")
    private Integer id;

    @NotNull
    @Column(name = "username", unique = true, length = 50)
    @JsonProperty("username")
    private String username;

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

//    @OneToMany(targetEntity = Goods.class, mappedBy = "user",cascade = CascadeType.REMOVE)
//    @JsonProperty("user_goods")
//    private Set<Goods> goods = new HashSet<>();

    @ManyToMany(targetEntity = User.class)
    @JsonProperty("users")
    @JoinTable(name = "user_relation", joinColumns = @JoinColumn(name = "active_id"), inverseJoinColumns = @JoinColumn(name = "passive_id"))
    private Set<User> users = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }


    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", state=" + state +
                ", role=" + role +
                ", mobile='" + mobile + '\'' +
                ", qq='" + qq + '\'' +
                '}';
    }
}
