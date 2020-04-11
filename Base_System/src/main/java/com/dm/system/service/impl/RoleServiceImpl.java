package com.dm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dm.system.entity.Role;
import com.dm.system.mapper.RoleMapper;
import com.dm.system.service.IRoleService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Override
    public List<Role> getUserRoles(Integer userId) {
        return baseMapper.getUserRoles(userId);
    }
}
