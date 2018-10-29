package com.wlb.forever.rpc.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @Auther: william
 * @Date: 18/10/18 09:58
 * @Description:
 */
@Component
@Slf4j
public class SpringBeanUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    public static Object getBean(String name) throws BeansException {
        return context.getBean(name);
    }

    public static <T> T getBean(Class<T> c) throws BeansException {
        return context.getBean(c);
    }

    public static String[] getBeansName(Class<?> c) {
        return context.getBeanNamesForType(c);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return context.getBean(name, clazz);
    }

    public static void printAllBeans() {
        if (context == null) {
            System.out.println("context=null");
        }
        String[] beans = context.getBeanDefinitionNames();
        for (String beanName : beans) {
            Class<?> beanType = context.getType(beanName);
            System.out.println("BeanName:" + beanName);
            System.out.println("Bean的类型：" + beanType);
            System.out.println("Bean所在的包：" + beanType.getPackage());
            System.out.println("Bean：" + context.getBean(beanName));
        }
    }

    public static String[] getBeanNames() throws BeansException {
        return context.getBeanDefinitionNames();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
