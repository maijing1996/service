package com.mj.service;

import com.mj.model.Subscribe;
import com.mj.repositories.SubscribeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mj on 2018-04-29.
 */
@Service
public class SubscribeService {
    @Autowired
    private SubscribeRepository subscribeRepository;

    public List<Subscribe> getSubscribe (Integer id, String nickName) {
        if (nickName == null) {
            nickName = "";
        }
        List<Subscribe> list = subscribeRepository.getSubscribe(id, nickName);
        return list;
    }
}
