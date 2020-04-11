package com.dm.core.annotation.auth;

import com.dm.core.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Aspect
@Order(-10)
@Component
public class PermissionsAspect {
    private static List<String> permis=new ArrayList<>();
    static {
        permis.add("user:view");
        permis.add("user:add");
    }

    /**
     * 拦截所有Permissions注解的方法
     */
    @Pointcut("@annotation(com.dm.core.annotation.auth.Permissions)")
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
        Permissions permissions = method.getAnnotation(Permissions.class);
        // 得到类名、方法名和参数
        Object target = jp.getTarget();
        String methodName = target.getClass().getName()+"."+method.getName();//方法名
        boolean auth=false;
        String[] perList = permissions.value();
        for (String p : perList) {
            if(permis.contains(p)){
                auth=true;
            }
        }
        if(auth){
            return jp.proceed();
        }else{
            log.info("========没有权限访问【"+methodName+"】========");
            return Result.fail(HttpStatus.FORBIDDEN, "用户无权限!", null);
        }
    }
}
