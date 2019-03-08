package com.mj.service;

import com.mj.model.User;

import java.util.List;

public interface UserService {

    User getUser(Integer id);

    User AuthenticateUser(String username , String password);

    void addUser(User user);

    void updateUser(User user, Integer id);

    void checkUsernameRepeat(User user);

    Boolean isExistUser (String openid);

    Integer getUserIdByOpenid(String openid);

    String getUsernameByOpenid(String openid);

    Integer getUserRoleByOpenid(String openid);

    Integer getUserStateByOpenid(String openid);

    void saveUser(User user);

    List<User> getAllUser(String nickName);


}
