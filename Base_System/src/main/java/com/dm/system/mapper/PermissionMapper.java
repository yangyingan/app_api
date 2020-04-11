package com.dm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dm.system.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> getRolePermissions(@Param("roleId") Integer roleId);
}
