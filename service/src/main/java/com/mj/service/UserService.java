package com.mj.service;

import com.github.pagehelper.PageInfo;
import com.mj.model.User;
import com.mj.model.dto.UserDto;

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

    /**
     * 所有的用户
     * @return
     */
    PageInfo<UserDto> listUser(Integer page, Integer size, String nickName);

    /**
     * 删除单个
     * @param id
     */
    void delete(Integer id);

    /**
     * 批量删除
     * @param ids
     */
    void deletes(String ids);
}
