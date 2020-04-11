package com.dm.core.pojo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 缓存用户信息
 */
@Data
public class CacheUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String username;

    private String nickname;

    private String phone;

    private String email;

    private Integer status;

    private String token;
}
