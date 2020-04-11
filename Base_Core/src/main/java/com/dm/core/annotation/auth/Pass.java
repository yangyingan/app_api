package com.dm.core.annotation.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Pass:在Controller方法上加入该注解不会验证身份
 *
 * @author lijun
 * @date 2019/12/30
 */
@Target( { ElementType.METHOD } )
@Retention( RetentionPolicy.RUNTIME )
public @interface Pass {
}
