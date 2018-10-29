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

    public static Object getRpcBean(String name, String methodName, Class[] classzz) {
        Object bean = getBean(name);
        if (bean == null) {
            String nameLower = name.toLowerCase();
            String[] beans = context.getBeanDefinitionNames();
            for (String beanName : beans) {
                String nameTmp = beanName.toLowerCase();
                if (nameTmp.indexOf(nameLower) > -1) {
                    Method mh = ReflectionUtils.findMethod(getBean(beanName).getClass(), methodName, classzz);
                    if (mh != null) {
                        return getBean(beanName);
                    }
                }
            }
        }
        return bean;
    }

    public static Object getBean(String name) throws BeansException {
        if (context.containsBean(name)) {
            return context.getBean(name);
        } else {
            try {
                context.getBean(Class.forName(name));
            } catch (ClassNotFoundException e) {
                log.error("获取bean(" + name + ")出现异常");
            }
        }
        return null;
    }

    public static <T> T getBean(Class<T> c) {
        try {
            return context.getBean(c);
        } catch (Exception e) {
            return null;
        }

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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
