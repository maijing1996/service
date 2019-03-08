package com.mj.mapper;

import com.mj.model.Subscribe;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SubscribeMapper extends Mapper<Subscribe> {

    List<Subscribe> getSubscribe(@Param("id") Integer id, @Param("nickName") String nickName);
}
