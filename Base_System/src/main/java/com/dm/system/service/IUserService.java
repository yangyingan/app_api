package com.dm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dm.core.pojo.CacheUser;
import com.dm.system.entity.User;

import java.util.List;

public interface IUserService extends IService<User> {
    /**
     * description: 根据用户名获取用户
     * create time: 2019/6/28 16:19
     * @param userName 用户名
     * @return 用户
     */
    User findByUsername(String userName);

    /**
     * description: 登录
     * create time: 2019/6/28 16:26
     * @param userName 用户名
     * @param password 密码
     * @return 用户信息
     */
    CacheUser login(String userName, String password);

    /**
     * description: 登出
     * create time: 2019/6/28 16:30
     */
    void logout();

    List<User> listUsers();
}
