package com.dm.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_permission")
public class Permission implements Serializable {
    @TableId(value = "permission_id", type = IdType.AUTO)
    private Integer permissionId;

    @TableField("parent_id")
    private Long parentId;

    @TableField("parent_ids")
    private String parentIds;

    @TableField("permission")
    private String permission;

    @TableField("permission_name")
    private String permissionName;

    @TableField("resource_type")
    private String resourceType;

    @TableField("url")
    private String url;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;
}
