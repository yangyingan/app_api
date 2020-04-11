package com.dm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dm.system.entity.Role;
import java.util.List;

public interface IRoleService extends IService<Role> {
    List<Role> getUserRoles(Integer userId);
}
