package com.mj.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.mj.enums.SubscribeEnum;
import com.mj.model.Subscribe;
import com.mj.model.SubscribeUser;
import com.mj.model.User;
import com.mj.service.Impl.UserServiceImpl;
import com.mj.service.Impl.SubscribeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mj on 2018-04-29.
 */
@RestController
public class SubscribeController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SubscribeServiceImpl subscribeService;

    @ResponseBody
    @PostMapping("/subscribe/addUser/{id}")
    public ResponseEntity<?> subscribe(@PathVariable Integer id,
                                       @RequestBody JsonNode jsonNode) throws Exception {
        User user = userService.getUser(id);
        Integer userId = jsonNode.path("userId").asInt();
//        Integer userId = Integer.valueOf(jsonNode.path("userId").textValue().trim());
        User OtherUser = userService.getUser(userId);
        user.getUsers().add(OtherUser);
        userService.saveUser(user);
        return ResponseEntity.ok(null);
    }

    /**
     * 查询某用户关注的所有用户
     *
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @GetMapping("/subscribe/getUsers/{id}")
    public ResponseEntity<?> getSubscribeUser(@PathVariable Integer id,
                                              @RequestParam(required = false, value = "nickName") String nickName,
                                              @RequestParam(value = "${spring.data.rest.page-param-name}", required = false, defaultValue = "${spring.data.rest.default-page-number}") Integer pageNum,
                                              @RequestParam(value = "${spring.data.rest.limit-param-name}", required = false, defaultValue = "${spring.data.rest.default-page-size}") Integer pageSize) {
        List<Subscribe> list = subscribeService.getSubscribe(id, nickName);
        return ResponseEntity.ok(list);
    }

    @ResponseBody
    @PostMapping("/subscribe/cancel/{id}")
    public ResponseEntity<?> cancelSubscribe(@PathVariable Integer id,
                                             @RequestBody JsonNode jsonNode) throws Exception {
        User user = userService.getUser(id);
        Integer passiveId = jsonNode.path("passiveId").asInt();
//        Integer passiveId = Integer.valueOf(jsonNode.path("passiveId").textValue().trim());
        User cancelUser = userService.getUser(passiveId);

        Iterator<User> iterator = user.getUsers().iterator();
        while (iterator.hasNext()) {
            User user1 = iterator.next();
            if (user1.equals(cancelUser)) {
                iterator.remove();
            }
        }

//        for (User user1 : user.getUsers()) {
//            if (user1.equals(cancelUser)) {
//                user.getUsers().remove(user1);
//            }
//        }
        userService.saveUser(user);
        return ResponseEntity.ok(null);
    }

    @ResponseBody
    @GetMapping("/subscribe/getAllUsers/{id}")
    public ResponseEntity<?> getSubscribeUserAndAllUser(@PathVariable Integer id,
                                                        @RequestParam(required = false, value = "nickName") String nickName) {
        if (nickName == null || "".equals(nickName)) {
            return ResponseEntity.ok(null);
        }
        List list = new ArrayList();
        List<User> userList = userService.getAllUser(nickName);
        List<Subscribe> subscribeList = subscribeService.getSubscribe(id, nickName);
        for (SubscribeUser subscribeUser : userList) {
            for (Subscribe subscribe : subscribeList) {
                if (subscribe.getId() == subscribeUser.getId()) {
                    subscribeUser.setSubscribe(SubscribeEnum.ALREADYSUBSCRIBE.getSubscribe());
                    continue;
                }
            }
            if (subscribeUser.getSubscribe() == null) {
                subscribeUser.setSubscribe(SubscribeEnum.UNSUBSCRIBE.getSubscribe());
            }
            if (subscribeUser.getId() != id) {
                list.add(subscribeUser);
            }
        }
        return ResponseEntity.ok(list);
    }
}
