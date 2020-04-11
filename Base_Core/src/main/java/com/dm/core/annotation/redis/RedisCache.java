package com.dm.core.annotation.redis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 元注解 Redis缓存
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {
    /**
     * 缓存key的名称
     * @return
     */
    String key();

    /**
     * key是否转换成md5值，有的key是整个参数对象，有的大内容的，比如一个大文本，导致redis的key很长
     * 需要转换成md5值作为redis的key
     * @return
     */
    boolean key2Md5() default false;

    /**
     * key 过期日期 秒
     * @return
     */
    int expireTime() default 60;

    /**
     * 时间单位，默认为秒
     * @return
     */
    TimeUnit dateUnit() default TimeUnit.SECONDS;
}
