package com.wlb.forever.rpc.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Auther: william
 * @Date: 18/10/18 09:58
 * @Description:
 */
@Component
@Slf4j
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext context;

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
        return context.getBean(c);
    }


    public static <T> T getBean(String name, Class<T> clazz) {
        return context.getBean(name, clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
