package com.mj.service;

import com.github.pagehelper.PageInfo;
import com.mj.model.Goods;

import java.util.List;

public interface GoodsService {

    Goods getGoodsById(Integer id);

    PageInfo<Goods> getGoods (Integer id, String goodsName, String type, Integer customerId, Integer pageNum, Integer pageSize);

    boolean saveGoods(Goods goods);

    void deleteGoods(Integer id);

    List<Goods> getAllGoodsByUser(Integer uid, String goodsName, String type);

}
