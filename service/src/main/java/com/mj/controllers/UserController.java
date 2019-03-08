package com.mj.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mj.enums.ErrorCode;
import com.mj.enums.ErrorMessage;
import com.mj.exceptions.UnAuthorizedException;
import com.mj.model.User;
import com.mj.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

/**
 * Created by mj on 2018-03-04.
 */
@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @ResponseBody
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @ResponseBody
    @PostMapping("/user/auth")
    public ResponseEntity<?> auth(@RequestBody JsonNode jsonNode) throws Exception {
        String username = jsonNode.path("username").textValue();
        String password = jsonNode.path("password").textValue();
        User user = userService.AuthenticateUser(username,password);
        return ResponseEntity.ok(user);
    }

    @ResponseBody
    @PostMapping("/user/register/{id}")
    public ResponseEntity<?> register(@PathVariable Integer id,@RequestBody JsonNode jsonNode) throws Exception {
        User user;
        try {
            user = new ObjectMapper().readValue(jsonNode.traverse(), User.class);
        } catch (IOException e) {
            throw new UnAuthorizedException(ErrorCode.JSON_TO_OBJECT_ERROR, ErrorMessage.ERROR_CHANGE_TYPE);
        }
        User oldUser = userService.getUser(id);
        oldUser.setUsername(user.getUsername());
        oldUser.setPassword(user.getPassword());
        oldUser.setQq(user.getQq());
        oldUser.setMobile(user.getMobile());
        userService.updateUser(oldUser, id);
        return ResponseEntity.ok(null);
    }

    @ResponseBody
    @PostMapping("/user/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody JsonNode jsonNode) throws Exception {
        User userUpdate;
        try {
            userUpdate = new ObjectMapper().readValue(jsonNode.traverse(), User.class);
        } catch (IOException e) {
            throw new UnAuthorizedException(ErrorCode.JSON_TO_OBJECT_ERROR, ErrorMessage.ERROR_CHANGE_TYPE);
        }
        userService.updateUser(userUpdate, id);
        return ResponseEntity.ok(null);
    }

    @ResponseBody
    @GetMapping("/users")
    public ResponseEntity<?> getAllUser(@RequestParam(value = "nickName", required = false) String nickName) {
        List<User> list = userService.getAllUser(nickName);
        return ResponseEntity.ok(list);
    }

    @ResponseBody
    @PostMapping("/admin/personal/management/{id}/state/{state}")
    public ResponseEntity<?> adminControllerUser(@PathVariable(value = "id") Integer id,
                                                 @PathVariable(value = "state") Integer state) {
        User user = userService.getUser(id);
        user.setState(state);
        userService.saveUser(user);
        return ResponseEntity.ok(state);
    }



}
