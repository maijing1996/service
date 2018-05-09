package com.gjj.controllers;

import com.gjj.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gjj on 2018-05-08.
 */
@RestController
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    /**
     * 累计注册人数
     * @return
     */
    @ResponseBody
    @GetMapping("/statistics/register")
    public Long registerCount(){
        Long registerUsers = statisticsService.registerCount();
        return registerUsers;
    }

    /**
     * 累计发布商品
     * @return
     */
    @ResponseBody
    @GetMapping("/statistics/goods/count")
    public Long goodsCount() {
        Long goodsCount = statisticsService.goodsCount();
        return goodsCount;
    }

    /**
     * 成交量
     * @return
     */
    @ResponseBody
    @GetMapping("/statistics/goods/isDeal/count")
    public Long isDealGoodsCount() {
        Long count = statisticsService.isDealGoodsCount();
        return count;
    }
}
