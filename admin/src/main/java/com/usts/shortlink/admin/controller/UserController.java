package com.usts.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.usts.shortlink.admin.common.convention.result.Result;
import com.usts.shortlink.admin.common.convention.result.Results;
import com.usts.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.usts.shortlink.admin.dto.resp.UserActualRespDTO;
import com.usts.shortlink.admin.dto.resp.UserRespDTO;
import com.usts.shortlink.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/api/shortlink/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username) {
        UserRespDTO result = userService.getUserByUsername(username);
        return Results.success(result);
    }

    @GetMapping("/api/shortlink/v1/actual/user/{username}")
    public Result<UserActualRespDTO> getActualUserByUsername(@PathVariable("username") String username) {
        UserRespDTO result = userService.getUserByUsername(username);
        return Results.success(BeanUtil.copyProperties(result, UserActualRespDTO.class));
    }

}
