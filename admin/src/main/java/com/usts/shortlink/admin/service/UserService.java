package com.usts.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.usts.shortlink.admin.dao.entity.UserDO;
import com.usts.shortlink.admin.dto.req.UserLoginReqDTO;
import com.usts.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.usts.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.usts.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.usts.shortlink.admin.dto.resp.UserRespDTO;

/**
 * 用户接口层
 */
public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 返回用户实体
     */
    UserRespDTO getUserByUsername(String username);

    /**
     * 查询用户名是否存在
     * @param username 用户名
     * @return 用户名存在返回true, 否在返回false
     */
    Boolean hasUsername(String username);

    /**
     * 注册用户
     * @param userRegisterReqDTO 注册用户接收参数
     */
    void register(UserRegisterReqDTO userRegisterReqDTO);

    /**
     * 修改用户信息
     * @param userUpdateReqDTO 修改用户信息接收参数
     */
    void update(UserUpdateReqDTO userUpdateReqDTO);

    /**
     * 用户登录请求信息
     * @param userLoginReqDTO 用户登录接收参数
     * @return token
     */
    UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO);

    /**
     * 检查用户是否登录
     * @param username
     * @param token
     * @return
     */
    Boolean checkLogin(String username, String token);
}
