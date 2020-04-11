package com.dm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dm.system.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
