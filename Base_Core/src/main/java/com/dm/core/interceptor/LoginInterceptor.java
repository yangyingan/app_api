package com.dm.core.interceptor;

import com.dm.core.annotation.auth.Pass;
import com.dm.core.annotation.redis.IRedisService;
import com.dm.core.constant.RedisKeyConstant;
import com.dm.core.exception.LoginException;
import com.dm.core.pojo.CacheUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private IRedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("开始进入登录拦截........");
        HandlerMethod hm = null;
        if (handler instanceof HandlerMethod){
             hm = (HandlerMethod)handler;
            Pass pass=hm.getMethodAnnotation(Pass.class);
            //判断方法是否包含Pass注解，包含则不需要登录直接放行
            if(pass != null ) {
                return true;
            }
        }
        String token = request.getHeader("Authorization");
        log.info("拦截到的token为:" + token);
        try {
            if(StringUtils.isNotEmpty(token)){
                CacheUser user=redisService.get(RedisKeyConstant.LOGIN_TOKEN+token,CacheUser.class);
                if(user==null){
                    throw new LoginException("登录已过期，请重新登录！");
                }
                redisService.set(RedisKeyConstant.LOGIN_TOKEN+token,user,60*60*6);//6小时
                return true;
            }else{
                throw new LoginException("请登录后再操作哦！");
            }
        }catch (Exception e){
            log.info("拦截器异常："+e.getMessage());
            throw new LoginException(e.getMessage());
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
