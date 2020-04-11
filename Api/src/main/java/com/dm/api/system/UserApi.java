package com.dm.api.system;

import com.dm.core.annotation.auth.Permissions;
import com.dm.core.annotation.redis.RedisCache;
import com.dm.core.annotation.repeatlock.RepeatLock;
import com.dm.core.pojo.Result;
import com.dm.system.entity.User;
import com.dm.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 用户管理
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserApi {
    @Autowired
    private IUserService userService;

    /**
     * 用户查询.
     */
    @RepeatLock
    @GetMapping("/list/v1")
    @Permissions("user:view11")
    @RedisCache(key = "users",key2Md5 = true)
    public Result listUsers(String username,String password){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<User> users = userService.listUsers();
        return Result.success("查询成功！", users);
    }

    /**
     * 用户添加;
     * @return
     */
    @PostMapping("/add/v1")
    @Permissions("user:add")//权限管理;
    public String userInfoAdd(){
        return "userAdd";
    }

    /**
     * 用户删除;
     * @return
     */
    @PostMapping("/delete/v1")
    @Permissions("user:del")//权限管理;
    public String userDel(){
        return "userDel";
    }
}
