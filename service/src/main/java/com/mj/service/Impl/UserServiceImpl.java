package com.mj.service.Impl;

import com.mj.enums.ErrorCode;
import com.mj.enums.ErrorMessage;
import com.mj.exceptions.BusinessException;
import com.mj.exceptions.UnAuthorizedException;
import com.mj.mapper.UserMapper;
import com.mj.model.User;
import com.mj.service.UserService;
import com.mj.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mj on 2018-03-04
 */
@Service
public class UserServiceImpl implements UserService {

    /*@Autowired
    private UserRepository userRepository;*/

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    public User getUser(Integer id) {
        User user;
        try {
            User user1 = new User();
            user1.setId(id);
            user = userMapper.selectOne(user1);
//            user = userRepository.getOne(id);
        } catch (BusinessException e) {
            throw new UnAuthorizedException(ErrorCode.USERNAME_NOT_EXIST, ErrorMessage.NOT_FOUND_USER);
        }
        return user;
    }

    /**
     * 鉴定用户
     * @param username
     * @param password
     * @return
     */
    public User AuthenticateUser(String username , String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new UnAuthorizedException(ErrorCode.EMPTY_USERNAME, ErrorMessage.EMPTY_LOGIN_NAME);
        }

        if (password == null || password.trim().isEmpty())
        {
            throw new UnAuthorizedException(ErrorCode.EMPTY_PASSWORD, ErrorMessage.EMPTY_PASSWORD);
        }
        User user = userMapper.selectByPrimaryKey(username.trim());
//        User user = userRepository.findByUsername(username.trim());

        if (user == null) {
//            throw new UnAuthorizedException(ErrorCode.USERNAME_NOT_EXIST, ErrorMessage.NOT_FOUND_USER);
            throw new UnAuthorizedException(ErrorCode.ERROR_PASSWORD, ErrorMessage.ERROR_LOGIN__NAME_OR_PASSWORD);
        }

        if (user.getPassword().equals(MD5Util.encode(password.trim()))) {
            return user;
        } else {
            throw new UnAuthorizedException(ErrorCode.ERROR_PASSWORD, ErrorMessage.ERROR_LOGIN__NAME_OR_PASSWORD);
        }
    }

    /**
     * 添加用户
     * @param user
     */
    public void addUser(User user) {
        checkUsernameRepeat(user);
        user.setPassword(MD5Util.encode(user.getPassword()));
        userMapper.insert(user);
//        userRepository.save(user);
    }

    /**
     *
     * 更新用户
     * @param user
     * @param id
     */
    public void updateUser(User user, Integer id) {
        User oldUser = User.builder().id(id).build();
//        User oldUser = userRepository.getOne(id);
//        if (user.getUsername().trim().equals(oldUser.getUsername().trim())) {
//            throw new UnAuthorizedException(ErrorCode.USERNAME_EXIST, ErrorMessage.USERNAME_EXIST);
//        }
        if (user != null) {
            oldUser.setPassword(MD5Util.encode(user.getPassword()));
            user.setId(id);
            checkUsernameRepeat(user);
            oldUser.setUsername(user.getUsername());
            oldUser.setMobile(user.getMobile());
            oldUser.setQq(user.getQq());
        }
        userMapper.updateByPrimaryKeySelective(oldUser);
//        userRepository.save(oldUser);
    }

    /**
     * 根据用户名校验是否重复
     * @param user
     */
    public void checkUsernameRepeat(User user) {
        User user2 = User.builder().username(user.getUsername()).build();
        User user1 = userMapper.selectOne(user);
//        User user1 = userRepository.findByUsername(user.getUsername().trim());
          if (user1 != null && !user.getId().equals(user1.getId())) {
              throw new UnAuthorizedException(ErrorCode.USERNAME_EXIST, ErrorMessage.USERNAME_EXIST);
          }
//        List<User> list =  userRepository.findAll();
//        for (User user1 : list) {
//            if (user.getUsername().trim().equals(user1.getUsername().trim())) {
//                throw new UnAuthorizedException(ErrorCode.USERNAME_EXIST, ErrorMessage.USERNAME_EXIST);
//            }
//        }
    }

    /**
     * 根据openid是否存在用户
     * @param openid
     * @return
     */
    public Boolean isExistUser (String openid) {
        User user = User.builder().openid(openid).build();
        User user2 = userMapper.selectOne(user);
        if(user2 == null) {
            return false;
        }
        return true;
    }

    /**
     * 添加微信授权用户
     * @param user
     */
    public void addWechatUser(User user) {
        userMapper.insert(user);
//        userRepository.save(user);
    }

    public Integer getUserIdByOpenid(String openid) {
        User user = User.builder().openid(openid).build();
        User user2 = userMapper.selectOne(user);
        return user2.getId();
    }

    public String getUsernameByOpenid(String openid) {
        User user = User.builder().openid(openid).build();
        User user2 = userMapper.selectOne(user);
        return user2.getUsername();
    }

    public Integer getUserRoleByOpenid(String openid) {
        User user = User.builder().openid(openid).build();
        User user2 = userMapper.selectOne(user);
//        userRepository.getUserRoleByOpenid(openid)
        return user2.getRole();
    }

    public Integer getUserStateByOpenid(String openid) {
//        userRepository.getUserStateByOpenid(openid);
        User user = User.builder().openid(openid).build();
        User user2 = userMapper.selectOne(user);
        return user2.getState();
    }

    /**
     * 保存用户
     * @param user
     */
    public void saveUser(User user) {
        userMapper.insert(user);
//        userRepository.save(user);
    }

    public List<User> getAllUser(String nickName) {
        /*BooleanBuilder booleanBuilder = new BooleanBuilder();
        QUser qUser = QUser.user;
        if (nickName != null && !nickName.trim().isEmpty()) {
            booleanBuilder.and(qUser.nickName.contains(nickName));
        }*/
        List<User> userList = userMapper.selectAll();
//        List<User> users = (List<User>) userRepository.findAll(booleanBuilder);
        return userList;

    }






}
