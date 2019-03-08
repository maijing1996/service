package com.mj.controllers;

import com.mj.enums.GoodsTypeEnum;
import com.mj.service.Impl.StatisticsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mj on 2018-05-08.
 */
@RestController
public class StatisticsController {

    @Autowired
    private StatisticsServiceImpl statisticsService;


    @ResponseBody
    @GetMapping("/statistics/userAndGoodsCount")
    public ResponseEntity<?> userAndGoodsCount(){
        List list = new ArrayList();
        Integer registerUsers = statisticsService.registerCount();
        Integer goodsCount = statisticsService.goodsCount();
        Integer count = statisticsService.isDealGoodsCount();
        list.add(registerUsers);
        list.add(goodsCount);
        list.add(count);
        return ResponseEntity.ok(list);

    }
    /**
     * 累计注册人数
     * @return
     */
    @ResponseBody
    @GetMapping("/statistics/register")
    public Integer registerCount(){
        Integer registerUsers = statisticsService.registerCount();
        return registerUsers;
    }

    /**
     * 累计发布商品
     * @return
     */
    @ResponseBody
    @GetMapping("/statistics/goods/count")
    public Integer goodsCount() {
        Integer goodsCount = statisticsService.goodsCount();
        return goodsCount;
    }

    /**
     * 成交量
     * @return
     */
    @ResponseBody
    @GetMapping("/statistics/goods/isDeal/count")
    public Integer isDealGoodsCount() {
        Integer count = statisticsService.isDealGoodsCount();
        return count;
    }

    /**
     * 统计类型goods数量
     * @return
     */
    @ResponseBody
    @GetMapping("/statistics/goods/type")
    public ResponseEntity<?> getGoodsCountByType() {
        List list = new ArrayList();
        for (GoodsTypeEnum goodsTypeEnum : GoodsTypeEnum.values()) {

            Integer count = statisticsService.getGoodsCountByType(goodsTypeEnum.getCode()) ;
            System.out.println(goodsTypeEnum.getCode() +" "+ count);
            list.add(count);
        }
        return ResponseEntity.ok(list);
    }
}
