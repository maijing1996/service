package com.mj.service.Impl;

import com.mj.mapper.GoodsMapper;
import com.mj.mapper.UserMapper;
import com.mj.model.Goods;
import com.mj.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mj on 2018-05-08.
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 用户注册数量
     * @return
     */
    public Integer registerCount() {
        Integer registerUsers = userMapper.selectAll().size();
        return registerUsers;
    }

    /**
     * 商品数量
     * @return
     */
    public Integer goodsCount() {
        Integer goodsCount = goodsMapper.selectAll().size();
//        Long goodsCount = goodsRepository.count();
        return goodsCount;
    }

    /**
     * 交易的商品数量
     * @return
     */
    public Integer isDealGoodsCount() {
       /* BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGoods qGoods = QGoods.goods;
        if (true) {
            booleanBuilder.and(qGoods.customerId.isNotNull());
        }
        Long count = goodsRepository.count(booleanBuilder);*/
       Integer count = goodsMapper.findDealGood();
        return count;
    }

    /**
     * 根据类型查询商品
     * @param goodsType
     * @return
     */
    public Integer getGoodsCountByType(String goodsType) {
        /*BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGoods qGoods = QGoods.goods;
        if (GoodsType != null && !GoodsType.trim().isEmpty()) {
            booleanBuilder.and(qGoods.type.eq(GoodsType));
        }
        Long  count = goodsRepository.count(booleanBuilder);*/
        Goods goods = Goods.builder().type(goodsType).build();
        List<Goods> select = goodsMapper.select(goods);
        return select.size();
    }
}
