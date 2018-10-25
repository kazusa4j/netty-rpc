package com.wlb.forever.rpc.client.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @Auther: william
 * @Date: 18/10/22 16:39
 * @Description:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcClient {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String serviceName() default "";

    String beanName() default "";
}
