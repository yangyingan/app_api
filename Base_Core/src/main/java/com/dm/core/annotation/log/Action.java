package com.dm.core.annotation.log;

import java.lang.annotation.*;

/**
 * Title:接口动作注解
 * User:yangyignan
 * Date:2018-09-06
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
    String title() default "";                                   //方法描述
    String author() default "";                               //作者
    Group group() default Group.SELECT;         //功能分组
    boolean writeLog() default false;                   //是否写日志
    public enum Group {SELECT,INSERT,UPDATE,DELETE,LOGIN,LOGOUT,TO_PAGE,DOWNLOAD,UPLOAD}
}
