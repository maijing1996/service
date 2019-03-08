package com.mj.service.Impl;

import com.mj.mapper.SubscribeMapper;
import com.mj.model.Subscribe;
import com.mj.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mj on 2018-04-29.
 */
@Service
public class SubscribeServiceImpl implements SubscribeService {
    /*@Autowired
    private SubscribeRepository subscribeRepository;*/

    @Autowired
    private SubscribeMapper subscribeMapper;

    public List<Subscribe> getSubscribe (Integer id, String nickName) {
        if (nickName == null) {
            nickName = "";
        }
        List<Subscribe> list = subscribeMapper.getSubscribe(id, nickName);
//        List<Subscribe> list = subscribeRepository.getSubscribe(id, nickName);
        return list;
    }
}
