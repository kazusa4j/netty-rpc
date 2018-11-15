package com.wlb.forever.rpc.client.config;

import com.wlb.forever.rpc.client.RpcClientStarter;
import com.wlb.forever.rpc.common.utils.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Auther: william
 * @Date: 18/11/7 09:24
 * @Description:
 */
@Slf4j
public class CustomPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {


    @Override
    protected Properties mergeProperties() throws IOException {
        Properties result = new Properties();
        // 加载父类的配置
        Properties mergeProperties = super.mergeProperties();
       log.error("mergeProperties长度:{}",mergeProperties.size());
        result.putAll(mergeProperties);
        Map<String, String> configs = loadConfigs();
        result.putAll(configs);
        return result;
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        // 原有逻辑
        super.processProperties(beanFactoryToProcess, props);
        // 写入到系统属性
        if (true) {
            // write all properties to system for spring boot
            Enumeration<?> propertyNames = props.propertyNames();
            while (propertyNames.hasMoreElements()) {
                String propertyName = (String) propertyNames.nextElement();
                String propertyValue = props.getProperty(propertyName);
                System.setProperty(propertyName, propertyValue);
            }
        }
    }

    private Map<String, String> loadConfigs() {
        Map<String, String> map = new HashMap<>();
        log.error("加载配置中心配置");
       /* while (true) {
            RpcClientStarter rpcClientStarter = SpringBeanUtil.getBean(RpcClientStarter.class);
            rpcClientStarter.start();
            if (RpcClientStarter.channel != null && RpcClientStarter.channel.isActive()) {
                break;
            } else {
                try {
                    log.warn("睡眠3秒");
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
        }*/
        map.put("logging.level", "warn");
        return map;
    }
}
