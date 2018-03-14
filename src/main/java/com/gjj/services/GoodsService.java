package com.gjj.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gjj.models.Goods;
import com.gjj.models.User;
import com.gjj.qModels.QGoods;
import com.gjj.repositories.GoodsRepository;
import com.gjj.repositories.UserRepository;
import com.gjj.utils.IgnoreProperty;
import com.gjj.utils.ObjectMapperBuilder;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by gjj on 2018-03-09 .
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    @IgnoreProperty(pojo = User.class, value = {"user_goods"})
    public Page<Map<String, Object>> getGoods (Integer id, String goodsName, String type, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGoods qGoods = QGoods.goods;
        if (id != null) {
            booleanBuilder.and(qGoods.user.id.eq(id));
        }
        if (goodsName != null && !goodsName.trim().isEmpty()) {
            booleanBuilder.and(qGoods.goodsName.contains(goodsName));
        }
        if (type !=null && !type.trim().isEmpty()) {
            booleanBuilder.and(qGoods.type.eq(type.trim()));
        }

        ObjectMapper objectMapper;
        Page<Goods> goods = goodsRepository.findAll(booleanBuilder,pageable);
        try {
            objectMapper = new ObjectMapperBuilder().build(GoodsService.class.getMethod("getGoods",Integer.class,String.class,String.class,Pageable.class));
            return new ObjectMapperBuilder().mapPagedObjects(goods,objectMapper);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Goods saveGoods(Goods goods) {
        goodsRepository.save(goods);
        return goods;
    }

    public void deleteGoods(Goods goods) {
        goodsRepository.delete(goods);
    }
}
