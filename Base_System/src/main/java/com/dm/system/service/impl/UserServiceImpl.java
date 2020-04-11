package com.dm.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dm.core.annotation.redis.IRedisService;
import com.dm.core.constant.RedisKeyConstant;
import com.dm.core.exception.LoginException;
import com.dm.core.pojo.CacheUser;
import com.dm.system.entity.User;
import com.dm.system.mapper.UserMapper;
import com.dm.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private IRedisService redisService;

    @Override
    public User findByUsername(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    public CacheUser login(String username, String password) {
        User user = findByUsername(username);
        if(user==null){
            throw new LoginException("用户名或密码错误");
        }
        if(!password.equals(user.getPassword())){
            throw new LoginException("用户名或密码错误");
        }
        return createToken(user);
    }

    @Override
    public void logout() {

    }

    @Override
    public List<User> listUsers() {
        return baseMapper.selectList(new LambdaQueryWrapper<>());
    }

    /**
     * 创建登录token
     */
    private CacheUser createToken(User user) {
        String uuid = UUID.randomUUID().toString();
        String str= uuid + user.getUserId()+System.currentTimeMillis();
        String token = DigestUtils.md5Hex(str);

        CacheUser cacheUser=new CacheUser();
        try {
            cacheUser.setUserId(user.getUserId());
            cacheUser.setNickname(user.getNickname());
            cacheUser.setUsername(user.getUsername());
            cacheUser.setPhone(user.getPhone());
            cacheUser.setEmail(user.getEmail());
            cacheUser.setStatus(user.getStatus());
            cacheUser.setToken(token);
            //将token放到redis，过期时间8小时
            redisService.set(RedisKeyConstant.LOGIN_TOKEN+token,cacheUser,60*60*8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheUser;
    }
}
