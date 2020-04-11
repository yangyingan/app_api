package com.dm.core.annotation.repeatlock;

import com.dm.core.annotation.redis.IRedisService;
import com.dm.core.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import javax.annotation.Resource;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@Order(-5)
@Component
public class RepeatLockAspect {
    private static final String REPEAT_KEY = "repeatlkey:";

    @Resource
    private IRedisService redisService;

    /**
     * 拦截所有RepeatLock注解的方法
     */
    @Pointcut("@annotation(com.dm.core.annotation.repeatlock.RepeatLock)")
    public void pointcutMethod(){ }

    @Around("pointcutMethod()")
    public Object lock(ProceedingJoinPoint jp) throws Throwable {
        String token = "admin";//需要重session中获取

        // 得到类名、方法名和参数
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        RepeatLock repeatLock = method.getAnnotation(RepeatLock.class);
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
        String paramKey= token+"#"+param.toString();
        String md5StrKey=DigestUtils.md5DigestAsHex(paramKey.getBytes());
        String redisKey=REPEAT_KEY + md5StrKey;
        log.info("========重复请求锁定参数【"+paramKey+"】========");

        Result result=Result.fail("程序正在处理，请稍后");
        try {
            Object value = redisService.get(redisKey);
            if(value!=null){
                return Result.fail("程序正在处理，不能重复请求哦！");
            }else{
                //生存时间去默认的10秒
                long liveTime = repeatLock.time2Live();
                redisService.set(redisKey,paramKey,liveTime);
            }
            Object proceed = jp.proceed();
            if (proceed instanceof Result) {
                 result = (Result) proceed;
                if(!result.getCode().equals(200)){
                    redisService.del(redisKey);
                }
            }
        } catch (Exception ex) {
            log.info("重复请求拦截异常");
            redisService.del(redisKey);
        }
        return result;
    }
}
