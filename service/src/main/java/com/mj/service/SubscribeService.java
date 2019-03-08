package com.mj.service;

import com.mj.model.Subscribe;

import java.util.List;

public interface SubscribeService {

    List<Subscribe> getSubscribe (Integer id, String nickName);
}
