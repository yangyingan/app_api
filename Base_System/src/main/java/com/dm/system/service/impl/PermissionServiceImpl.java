package com.dm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dm.system.entity.Permission;
import com.dm.system.mapper.PermissionMapper;
import com.dm.system.service.IPermissionService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
    @Override
    public List<Permission> getRolePermissions(Integer roleId) {
        return baseMapper.getRolePermissions(roleId);
    }
}
