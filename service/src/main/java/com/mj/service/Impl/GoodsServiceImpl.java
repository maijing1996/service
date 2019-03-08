package com.mj.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mj.enums.ErrorCode;
import com.mj.enums.ErrorMessage;
import com.mj.exceptions.UnAuthorizedException;
import com.mj.mapper.GoodsMapper;
import com.mj.model.Goods;
import com.mj.model.User;
import com.mj.service.GoodsService;
import com.mj.utils.IgnoreProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mj on 2018-03-09 .
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    /*@Autowired
    private GoodsRepository goodsRepository;*/

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 根据id获取商品信息
     * @param id
     * @return
     */
    public Goods getGoodsById(Integer id) {
        Goods goods = Goods.builder().id(id).build();
        Goods goods1 = goodsMapper.selectOne(goods);
        if (goods1 == null) {
                throw new UnAuthorizedException(ErrorCode.NOT_FOUND_GOODS, ErrorMessage.NOT_FOUND_GOODS);
        }
        return goods1;
    }

    @IgnoreProperty(pojo = User.class, value = {"user_goods"})
    public PageInfo<Goods> getGoods (Integer id, String goodsName, String type, Integer customerId, Integer pageNum, Integer pageSize) {

        if(pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        } else {
            PageHelper.startPage(1, 20);
        }
        List<Goods> goodsList =  goodsMapper.findAll(id, goodsName, type, customerId);
        PageInfo<Goods> pageInfo = new PageInfo<Goods>(goodsList);

        return pageInfo;
    }

    /**
     * 保存商品
     * @param goods
     * @return
     */
//    @IgnoreProperty(pojo = User.class, value = {"user_goods"})
    public boolean saveGoods(Goods goods) {
//        ObjectMapper objectMapper;
//        int insert = goodsMapper.insertSelective(goods);
        Integer insert = goodsMapper.insertAndGetId(goods);
//        Goods goods1 = goodsRepository.save(goods);
//        try {
//            objectMapper = new ObjectMapperBuilder().build(GoodsService.class.getMethod("saveGoods",Goods.class));
//            return new ObjectMapperBuilder().mapObjects(goods1,objectMapper);
//        }  catch (Exception e) {
//            e.printStackTrace();
//        }
        Integer id = goods.getId();
        return insert > 0;
    }

    public void deleteGoods(Integer id) {
        Goods goods = Goods.builder().id(id).build();
        goodsMapper.delete(goods);
//        goodsRepository.delete(id);
    }

    /**
     * 获取某人的所有商品
     */
    public List<Goods> getAllGoodsByUser(Integer uid, String goodsName, String type) {

        List<Goods> list = goodsMapper.findAllUser(uid, goodsName, type);
//        List<Goods> list = (List<Goods>)goodsRepository.findAll(booleanBuilder);
        return list;
    }
}
