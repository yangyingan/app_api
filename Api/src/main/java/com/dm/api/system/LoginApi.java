package com.dm.api.system;

import com.dm.core.pojo.CacheUser;
import com.dm.core.pojo.Result;
import com.dm.system.entity.User;
import com.dm.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * 登录
 */
@Slf4j
@RestController
public class LoginApi {
    @Resource
    private IUserService userService;

    /**
     * 登录
     * @param user
     * @return
     */
    @GetMapping("/login/v1")
    public Result login(User user) {
        log.warn("进入登录.....");
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isBlank(username)) {
            return Result.fail("用户名不能为空！");
        }
        if (StringUtils.isBlank(password)) {
            return Result.fail("密码不能为空！");
        }
        CacheUser cacheUser = userService.login(username, password);
        // 登录成功返回用户信息
        return Result.success("登录成功！", cacheUser);
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping("/logout/v1")
    public Result logOut() {

        return Result.success("退出成功！");
    }
}
