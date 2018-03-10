package com.gjj.services;

import com.gjj.models.Goods;
import com.gjj.models.User;
import com.gjj.qModels.QGoods;
import com.gjj.repositories.GoodsRepository;
import com.gjj.repositories.UserRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by gjj on 2018-03-09 .
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<Goods> getGoods (Integer id, Integer pageNumber, Integer pageSize) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGoods qGoods = QGoods.goods;
        if (id != null) {
            User user = userRepository.getOne(id);
            booleanBuilder.and(qGoods.user.eq(user));
        }
        Sort sort = new Sort(Sort.Direction.DESC, "bulletinDate");
        Pageable pageable = new PageRequest(pageNumber, pageSize, sort);
        Page<Goods> goods = goodsRepository.findAll(booleanBuilder,pageable);
        return goods;
    }

    public Goods saveGoods(Goods goods) {
//        goods.setUser(userRepository.getOne(1));
        goodsRepository.save(goods);
        return goods;
    }

    public void deleteGoods(Goods goods) {
        goodsRepository.delete(goods);
    }
}
