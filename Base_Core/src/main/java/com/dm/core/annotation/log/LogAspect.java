package com.dm.core.annotation.log;//package com.leigq.www.shiro.config.annotation.log;
//
//import com.alibaba.fastjson.JSONObject;
//import com.td.core.annotation.Action;
//import com.td.core.context.UserContext;
//import com.td.core.sys.entity.Log;
//import com.td.core.sys.entity.User;
//import com.td.core.sys.service.LogService;
//import com.td.core.utils.BrowserUtil;
//import com.td.core.utils.IpUtil;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.lang.reflect.Method;
//import java.util.Date;
//import java.util.Enumeration;
//
///**
// * @title:  日志处理AOP  切面类
// * @date:   2018-09-06
// * @author: yangyignan
// */
//@Aspect
//@Component
//public class LogAspect {
//    private Logger logger = LoggerFactory.getLogger(LogAspect.class);
//    private ThreadLocal<Log> sysLog=new ThreadLocal<Log>();//日志实体
//    private ThreadLocal<Long> startTime = new ThreadLocal<>();//执行开始时间
//    private ThreadLocal<Boolean> writeLog=new ThreadLocal<>();//是否写日志
//
//    @Autowired
//    private LogService logService;
//
//    @Pointcut("@annotation(com.td.core.annotation.Action)")
//    public void webLog(){}
//
//    @Before("webLog()")
//    public void doBefore(JoinPoint joinPoint) {
//        sysLog.set(new Log());
//        startTime.set(System.currentTimeMillis());
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        HttpSession session = (HttpSession) attributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
//        if(session != null){
//            logger.info("sessionId :======>" + session.getId());
//        }
//        logger.info("请求url:======>"+request.getRequestURL());
//        logger.info("请求方法:======>" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//        logger.info("请求方式:======>" +request.getMethod());
//        //获取传入目标方法的参数
//        StringBuilder sbParam=new StringBuilder();
//        Enumeration enumeration = request.getParameterNames();
//        while (enumeration.hasMoreElements()) {
//            String name = enumeration.nextElement().toString();
//            if(sbParam.length()==0){
//                sbParam.append(name + "=" + request.getParameter(name));
//            }else{
//                sbParam.append(","+name + "=" + request.getParameter(name));
//            }
//        }
//        logger.info("请求参数:======>" + "["+sbParam+"]");
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//        Action action = method.getAnnotation(com.td.core.annotation.Action.class);
//        if(action != null){  //注解上的描述
//            logger.info("动作名称:======>" + action.title());
//            logger.info("动作分组:======>" + action.group());
//            logger.info("是否写日志:======>" + action.writeLog());
//            writeLog.set(action.writeLog());
//            sysLog.get().setExecMethodDesc(action.title());//执行方法描述
//            sysLog.get().setLogType(action.group().toString());//分组
//            sysLog.get().setLogContent(action.title());//日志内容
//        }
//        sysLog.get().setReqDate(new Date());
//        sysLog.get().setReqIp(IpUtil.getIpAddr(request));
//        sysLog.get().setReqBrowser(BrowserUtil.checkBrowse(request));
//        sysLog.get().setReqType(request.getMethod());//POST   GET
//        sysLog.get().setReqUrl(request.getRequestURL().toString());//请求url
//        sysLog.get().setReqParam("["+sbParam+"]");
//        sysLog.get().setExecClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//        sysLog.get().setLogLevel("INFO");
//    }
//
//    @AfterReturning(returning = "returnObj", pointcut = "webLog()")
//    public void doAfterReturning(Object returnObj) {
//        String retString = JSONObject.toJSONString(returnObj);
//        retString = retString.length() > 2000 ? retString.substring(2000) : retString;
//        logger.info("返回结果:======>" + retString);
//        logger.info("执行时间:======>" + (System.currentTimeMillis() - startTime.get()) + "毫秒");
//
//        User currUser=UserContext.getCurrentUser();
//        if(currUser!=null){
//            sysLog.get().setReqUserid(currUser.getId());
//            sysLog.get().setReqUsername(currUser.getUserName());
//            sysLog.get().setReqRealname(currUser.getRealName());
//        }
//        sysLog.get().setResResult(retString);//返回结果
//        sysLog.get().setExecMethodTime((System.currentTimeMillis() - startTime.get()));//"毫秒"
//        startTime.remove();
//        sysLog.get().setResStatus("200");
//        if(writeLog.get()){
////            logService.save(sysLog.get());
//            LogQueue.addLog(sysLog.get());
//            //异步队列保存日志到数据库
//            writeLog.remove();
//            sysLog.remove();
//        }
//    }
//}
