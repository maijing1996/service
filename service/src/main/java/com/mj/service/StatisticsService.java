package com.mj.service;

public interface StatisticsService {

    Integer registerCount();

    Integer goodsCount();

    Integer isDealGoodsCount();

    Integer getGoodsCountByType(String goodsType);

}
