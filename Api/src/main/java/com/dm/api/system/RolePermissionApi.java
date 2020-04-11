package com.dm.api.system;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/role-permission")
public class RolePermissionApi {

//    角色有哪些权限（角色权限树）
//    select
//    r.role_id,
//    r.role_code,
//    r.role_name,
//    p.permission_id,
//    p.permission,
//    p.permission_name,
//    p.resource_type,
//    p.url,
//    p.parent_id
//    from sys_role r,sys_role_permission rp,sys_permission p
//    where r.role_id=rp.role_id and rp.permission_id=p.permission_id

//    角色有哪些用户（传入角色id，进行分页）
//    select distinct
//    r.role_id,
//    r.role_code,
//    r.role_name,
//    u.user_id,
//    u.username,
//    u.nickname,
//    u.phone,
//    u.email,
//    u.`status`
//    from sys_user u,sys_role r,sys_user_role ur
//    where u.user_id=ur.user_id and r.role_id=ur.role_id;


//    用户有哪些权限（用户权限树）
//    select distinct
//    u.user_id,
//    u.username,
//    u.nickname,
//    u.phone,
//    u.email,
//    u.`status`,
//    p.permission_id,
//    p.permission,
//    p.permission_name,
//    p.resource_type,
//    p.url,
//    p.parent_id
//    from sys_user u,sys_role r,sys_permission p,sys_user_role ur,sys_role_permission rp
//    where u.user_id=ur.user_id and r.role_id=ur.role_id and rp.role_id=r.role_id and rp.permission_id=p.permission_id


//    用户有哪些角色（用户角色列表）
//    select distinct
//    u.user_id,
//    u.username,
//    u.nickname,
//    u.phone,
//    u.email,
//    u.`status`,
//    r.role_id,
//    r.role_code,
//    r.role_name
//    from sys_user u,sys_role r,sys_user_role ur
//    where u.user_id=ur.user_id and r.role_id=ur.role_id
}
