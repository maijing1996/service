package com.gjj.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gjj.enums.ErrorCode;
import com.gjj.enums.ErrorMessage;
import com.gjj.exceptions.UnAuthorizedException;
import com.gjj.models.User;
import com.gjj.services.AuthenticationUserService;
import com.gjj.utils.IgnoreProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

/**
 * Created by gjj on 2018-03-04.
 */
@RestController
public class UserController {
    @Autowired
    private AuthenticationUserService authenticationUserService;

    @ResponseBody
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {
        User user = authenticationUserService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @ResponseBody
    @PostMapping("/user/auth")
    public ResponseEntity<?> auth(@RequestBody JsonNode jsonNode) throws Exception {
        String username = jsonNode.path("username").textValue();
        String password = jsonNode.path("password").textValue();
        User user = authenticationUserService.AuthenticateUser(username,password);
        return ResponseEntity.ok(user);
    }

    @ResponseBody
    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody JsonNode jsonNode) throws Exception {
        User user;
        try {
            user = new ObjectMapper().readValue(jsonNode.traverse(), User.class);
        } catch (IOException e) {
            throw new UnAuthorizedException(ErrorCode.JSON_TO_OBJECT_ERROR, ErrorMessage.ERROR_CHANGE_TYPE);
        }
        authenticationUserService.addUser(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @ResponseBody
    @PatchMapping("/user/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody JsonNode jsonNode) throws Exception {
        User userUpdate;
        try {
            userUpdate = new ObjectMapper().readValue(jsonNode.traverse(), User.class);
        } catch (IOException e) {
            throw new UnAuthorizedException(ErrorCode.JSON_TO_OBJECT_ERROR, ErrorMessage.ERROR_CHANGE_TYPE);
        }
        authenticationUserService.updateUser(userUpdate, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }




}
