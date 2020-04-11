package com.dm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dm.system.entity.Permission;
import java.util.List;

public interface IPermissionService extends IService<Permission> {
    List<Permission> getRolePermissions(Integer roleId);
}
