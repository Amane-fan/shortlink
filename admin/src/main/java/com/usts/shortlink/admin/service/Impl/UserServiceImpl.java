package com.usts.shortlink.admin.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usts.shortlink.admin.common.convention.exception.ClientException;
import com.usts.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.usts.shortlink.admin.dao.entity.UserDO;
import com.usts.shortlink.admin.dao.mapper.UserMapper;
import com.usts.shortlink.admin.dto.req.UserLoginReqDTO;
import com.usts.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.usts.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.usts.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.usts.shortlink.admin.dto.resp.UserRespDTO;
import com.usts.shortlink.admin.service.UserService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.usts.shortlink.admin.common.constant.RedisCacheConstant.*;
import static com.usts.shortlink.admin.common.enums.UserErrorCodeEnum.USER_NAME_EXIST;
import static com.usts.shortlink.admin.common.enums.UserErrorCodeEnum.USER_SAVE_ERROR;

/**
 * 用户接口实现层
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Autowired
    private RBloomFilter<String> rBloomFilter;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null) {
            throw new ClientException(UserErrorCodeEnum.USER_NULL);
        }
        UserRespDTO result = new UserRespDTO();
        BeanUtils.copyProperties(userDO, result);
        return result;
    }

    @Override
    public Boolean hasUsername(String username) {
        return rBloomFilter.contains(username);
    }

    @Override
    public void register(UserRegisterReqDTO userRegisterReqDTO) {
        if (hasUsername(userRegisterReqDTO.getUsername())) {
            throw new ClientException(USER_NAME_EXIST);
        }
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + userRegisterReqDTO.getUsername());
        try {
            // 如果成功获取锁，肯定能够注册成功
            if (lock.tryLock()) {
                int inserted = baseMapper.insert(BeanUtil.copyProperties(userRegisterReqDTO, UserDO.class));
                if (inserted < 1) {
                    throw new ClientException(USER_SAVE_ERROR);
                }
                rBloomFilter.add(userRegisterReqDTO.getUsername());
            } else {
                // 没获取到锁，不需要等待，直接抛出异常即可
                throw new ClientException(USER_NAME_EXIST);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void update(UserUpdateReqDTO userUpdateReqDTO) {
        // TODO 验证当前用户名是否为登录用户
        LambdaQueryWrapper<UserDO> updateWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, userUpdateReqDTO.getUsername());
        baseMapper.update(BeanUtil.copyProperties(userUpdateReqDTO, UserDO.class), updateWrapper);
    }


    @Override
    public UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO) {
        // 检查该用户名和密码是否存在
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, userLoginReqDTO.getUsername())
                .eq(UserDO::getPassword, userLoginReqDTO.getPassword())
                .eq(UserDO::getDelFlag, 0);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null) {
            throw new ClientException("用户不存在");
        }

        String userKey = USER_LOGIN + userLoginReqDTO.getUsername();

        if (stringRedisTemplate.hasKey(userKey)) {
            throw new ClientException("用户已登录");
        }

        String token = UUID.randomUUID().toString();
        String tokenKey = TOKEN_LOGIN + token;

        stringRedisTemplate.opsForValue().set(userKey, token, 30L, TimeUnit.MINUTES);
        stringRedisTemplate.opsForValue().set(tokenKey, JSON.toJSONString(userDO), 30L, TimeUnit.MINUTES);

        return new UserLoginRespDTO(token);
    }

    @Override
    public Boolean checkLogin(String username, String token) {
        return stringRedisTemplate.hasKey(USER_LOGIN + username);
    }
}
