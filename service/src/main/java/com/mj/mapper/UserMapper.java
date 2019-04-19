package com.mj.mapper;

import com.mj.model.User;
import com.mj.model.dto.UserDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserMapper extends Mapper<User> {

    User findById(@Param("id") Integer id);

    List<UserDto> listUser(@Param("nickName") String nickName);

    void deletes(@Param("ids") String ids);
}
