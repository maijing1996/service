package com.gjj.services;

import com.gjj.enums.ErrorCode;
import com.gjj.enums.ErrorMessage;
import com.gjj.exceptions.BusinessException;
import com.gjj.exceptions.UnAuthorizedException;
import com.gjj.models.Goods;
import com.gjj.models.User;
import com.gjj.repositories.UserRepository;
import com.gjj.utils.IgnoreProperty;
import com.gjj.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by gjj on 2018-03-04
 */
@Service
public class AuthenticationUserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(Integer id) {
        User user;
        try {
            user = userRepository.getOne(id);
        } catch (BusinessException e) {
            throw new UnAuthorizedException(ErrorCode.USERNAME_NOT_EXIST, ErrorMessage.NOT_FOUND_USER);
        }
        return user;
    }

    public User AuthenticateUser(String username , String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new UnAuthorizedException(ErrorCode.EMPTY_USERNAME, ErrorMessage.EMPTY_LOGIN_NAME);
        }

        if (password == null || password.trim().isEmpty())
        {
            throw new UnAuthorizedException(ErrorCode.EMPTY_PASSWORD, ErrorMessage.EMPTY_PASSWORD);
        }
        User user = userRepository.findByUsername(username.trim());

        if (user == null) {
            throw new UnAuthorizedException(ErrorCode.USERNAME_NOT_EXIST, ErrorMessage.NOT_FOUND_USER);
        }

        if (user.getPassword().equals(MD5Util.encode(password.trim()))) {
            return user;
        } else {
            throw new UnAuthorizedException(ErrorCode.ERROR_PASSWORD, ErrorMessage.ERROR_LOGIN__NAME_OR_PASSWORD);
        }
    }

    public void addUser(User user) {
        checkUsernameRepeat(user);
        user.setPassword(MD5Util.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void updateUser(User user, int id) {
        User oldUser = userRepository.getOne(id);
        if (user.getUsername().trim().equals(oldUser.getUsername().trim())) {
            throw new UnAuthorizedException(ErrorCode.USERNAME_EXIST, ErrorMessage.USERNAME_EXIST);
        }
        oldUser.setUsername(user.getUsername().trim());
        oldUser.setMobile(user.getMobile().trim());
        oldUser.setQq(user.getQq().trim());
        userRepository.save(oldUser);
    }

    public void checkUsernameRepeat(User user) {
        List<User> list =  userRepository.findAll();
        for (User user1 : list) {
            if (user.getUsername().trim().equals(user1.getUsername().trim())) {
                throw new UnAuthorizedException(ErrorCode.USERNAME_EXIST, ErrorMessage.USERNAME_EXIST);
            }
        }
    }

    public Boolean isExistUser (String openid) {
        if(userRepository.findByOpenid(openid) == null) {
            return false;
        }
        return true;
    }

    public void addWechatUser(User user) {
        userRepository.save(user);
    }

    public Integer getUserIdByOpenid(String openid) {
        return userRepository.getUserIdByOpenid(openid);
    }

    public String getUsernameByOpenid(String openid) {
        return userRepository.getUsernameByOpenid(openid);
    }






}
