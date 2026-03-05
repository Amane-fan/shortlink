package com.usts.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.usts.shortlink.admin.common.convention.result.Result;
import com.usts.shortlink.admin.common.convention.result.Results;
import com.usts.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.usts.shortlink.admin.dto.resp.UserActualRespDTO;
import com.usts.shortlink.admin.dto.resp.UserRespDTO;
import com.usts.shortlink.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制层
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据用户名查询用户信息
     */
    @GetMapping("/api/short-link/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username) {
        UserRespDTO result = userService.getUserByUsername(username);
        return Results.success(result);
    }

    /**
     * 根据用户名查询用户无脱敏信息
     */
    @GetMapping("/api/short-link/v1/actual/user/{username}")
    public Result<UserActualRespDTO> getActualUserByUsername(@PathVariable("username") String username) {
        UserRespDTO result = userService.getUserByUsername(username);
        return Results.success(BeanUtil.copyProperties(result, UserActualRespDTO.class));
    }

    /**
     * 查询用户名是否存在
     */
    @GetMapping("/api/short-link/v1/user/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username) {
        Boolean result = userService.hasUsername(username);
        return Results.success(result);
    }

    /**
     * 注册用户
     * @param userRegisterReqDTO
     * @return
     */
    @PostMapping("/api/short-link/v1/user")
    public Result<Void> register(@RequestBody UserRegisterReqDTO userRegisterReqDTO) {
        userService.register(userRegisterReqDTO);
        return Results.success();
    }

}
