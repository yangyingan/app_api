package com.dm.core.annotation.redis;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
public class RedisCacheAspect {
    @Resource
    private IRedisService redisService;

    /**
     * 拦截所有RedisCache注解的方法
     */
    @Pointcut("@annotation(com.dm.core.annotation.redis.RedisCache)")
    public void pointcutMethod(){ }

    /**
     * 具体的方法
     * @param jp
     * @return
     */
    @Around("pointcutMethod()")
    public Object cache(ProceedingJoinPoint jp) throws Throwable{
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        RedisCache redisCache = method.getAnnotation(RedisCache.class);
        // 得到类名、方法名和参数
        Object target = jp.getTarget();
        String className = target.getClass().getName();
        String methodName = method.getName();

        StringBuilder param=new StringBuilder();
        param.append(className).append(".").append(methodName);
        Object[] params = jp.getArgs();
        for(Object p:params){
            if(p==null){
                continue;
            }
            param.append("#").append(p);
        }
        //是否转换成md5值
        boolean key2Md5 = redisCache.key2Md5();
        String redisKey=redisCache.key() +":"+ param.toString();
        if(key2Md5){
            String paramKey = param.toString();
            String md5StrKey=DigestUtils.md5DigestAsHex(paramKey.getBytes());
            redisKey=redisCache.key() +":"+ md5StrKey;
        }
        log.info("========缓存RedisKey="+redisKey+"========");

        Object result = null;
        Class type = ((MethodSignature) jp.getSignature()).getReturnType();
        Object value = redisService.get(redisKey,type);
        if (value == null) {
            log.info("========Redis缓存未命中，从数据库获取数据========");
            result = jp.proceed(params);
            redisService.set(redisKey,result,getExpireTimeSeconds(redisCache));
        } else {
            log.info("========Redis缓存命中，从Redis获取数据========");
            result=value;
        }
        return result;
    }

    /**
     * 计算根据Cacheable注解的expire和DateUnit计算要缓存的秒数
     * @param redisCache
     * @return
     */
    public int getExpireTimeSeconds(RedisCache redisCache) {
        int expire = redisCache.expireTime();
        TimeUnit unit = redisCache.dateUnit();
        if (expire <= 0) {//传入非法值，默认一分钟,60秒
            return 60;
        }
        if (unit == TimeUnit.MINUTES) {
            return expire * 60;
        } else if(unit == TimeUnit.HOURS) {
            return expire * 60 * 60;
        } else if(unit == TimeUnit.DAYS) {
            return expire * 60 * 60 * 24;
        }else {//什么都不是，默认一分钟,60秒
            return 60;
        }
    }
}
