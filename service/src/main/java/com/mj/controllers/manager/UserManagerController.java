package com.mj.controllers.manager;

import com.github.pagehelper.PageInfo;
import com.mj.model.dto.UserDto;
import com.mj.model.request.UserRequest;
import com.mj.model.response.LayuiResponse;
import com.mj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ResponseBody
@RestController
@RequestMapping("/public/manager/user")
public class UserManagerController {

    @Autowired
    private UserService userService;

    /**
     * 查询所有的数据
     * @return
     */
    @PostMapping("/info")
    public LayuiResponse listUserInfo(@RequestBody UserRequest userRequest){
        LayuiResponse layuiResponse = new LayuiResponse();
        PageInfo<UserDto> allUser = userService.listUser(userRequest.getPage(), userRequest.getSize(), userRequest.getNickName());
        return layuiResponse.setSuccess("查询成功！",allUser.getList(),allUser.getTotal());
    }

    /**
     * 删除单个
     * @return
     */
    @PostMapping("/delete")
    public LayuiResponse deleteUser(@RequestBody UserRequest userRequest){
        LayuiResponse layuiResponse = new LayuiResponse();
        userService.delete(userRequest.getId());
        return layuiResponse.setSuccess("删除成功！",null,0);
    }

    /**
     * 批量删除
     * @return
     */
    @PostMapping("/deletes")
    public LayuiResponse deletesUser(@RequestBody UserRequest userRequest){
        LayuiResponse layuiResponse = new LayuiResponse();
        try{
            userService.deletes(userRequest.getIds());
        }catch (Exception e){
            log.info(e.getMessage(), e);
        }
        return layuiResponse.setSuccess("删除成功！",null,0);
    }

}
