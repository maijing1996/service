package com.mj.mapper;

import com.mj.model.Goods;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface GoodsMapper extends Mapper<Goods> {
    List<Goods> findAll(@Param("id") Integer id, @Param("goodsName") String goodsName, @Param("type") String type, @Param("customerId") Integer customerId);

    List<Goods> findAllUser(@Param("uid") Integer uid, @Param("goodsName") String goodsName, @Param("type") String type);

    Integer findDealGood();

    Integer insertAndGetId(Goods goods);
}
