package com.dm.core.annotation.redis;

import java.util.List;

/**
 * service:
 *
 * @author lijun
 * @date 2019/12/28
 */
public interface IRedisService {

    boolean set(String key, Object value) throws Exception;

    boolean set(String key, Object value, long expireInSeconds) throws Exception;

    Object get(String key) throws Exception;

    <T> T get(String key, Class<T> clz) throws Exception;

    boolean setNx(String key, Object value);

    boolean setNx(String key, Object value, long expireSeconds);

    boolean expire(String key, long expire) throws Exception;

    Long getExpire(String key) throws Exception;

    Long incr(String key) throws Exception;

    Long incrBy(String key, long stepValue) throws Exception;

    Long decr(String key) throws Exception;

    Long decrBy(String key, long stepValue) throws Exception;

    <T> boolean setList(String key, List<T> list) throws Exception;

    <T> List<T> getList(String key, Class<T> clz) throws Exception;

    void del(String key) throws Exception;

    long lpush(String key, Object obj) throws Exception;

    long rpush(String key, Object obj) throws Exception;

    void hmset(String key, Object obj) throws Exception;

    <T> T hget(String key, Class<T> clz) throws Exception;

    <T> List<T>  hmGetAll(String key, Class<T> clz) throws Exception;

    String lpop(String key) throws Exception;
}

