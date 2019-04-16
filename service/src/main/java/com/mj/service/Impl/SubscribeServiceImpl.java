package com.mj.service.Impl;

import com.mj.mapper.SubscribeMapper;
import com.mj.mapper.UserRelationMapper;
import com.mj.model.Subscribe;
import com.mj.model.User;
import com.mj.model.UserRelation;
import com.mj.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mj on 2018-04-29.
 */
@Service
public class SubscribeServiceImpl implements SubscribeService {

    @Autowired
    private SubscribeMapper subscribeMapper;
    @Autowired
    private UserRelationMapper userRelationMapper;

    public List<Subscribe> getSubscribe (Integer id, String nickName) {
        if (nickName == null) {
            nickName = "";
        }

        return subscribeMapper.getSubscribe(id, nickName);
    }

    /**
     * 保存关注
     * @param user
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveSubscribe(User user, Integer userId) {
        Subscribe subscribe = Subscribe.builder()
                .avatarUrl(user.getAvatarUrl())
                .id(user.getId())
                .gender(user.getGender())
                .nickName(user.getNickName())
                .build();
        subscribeMapper.insert(subscribe);

        UserRelation userRelation = UserRelation.builder()
                .activeId(user.getId())
                .passiveId(userId)
                .build();
        userRelationMapper.insert(userRelation);
    }
}
