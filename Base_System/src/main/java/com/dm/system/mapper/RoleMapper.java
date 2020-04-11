package com.dm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dm.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> getUserRoles(@Param("userId") Integer userId);
}
