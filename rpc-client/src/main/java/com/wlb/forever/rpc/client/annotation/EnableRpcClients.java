package com.wlb.forever.rpc.client.annotation;

import com.wlb.forever.rpc.client.wrapper.RpcClientsRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Auther: william
 * @Date: 18/10/22 16:50
 * @Description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RpcClientsRegistrar.class)
public @interface EnableRpcClients {

    String[] value() default {};


    String[] basePackages() default {};


    Class<?>[] basePackageClasses() default {};


    Class<?>[] defaultConfiguration() default {};


    Class<?>[] clients() default {};
}
