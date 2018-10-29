package com.wlb.forever.rpc.client.wrapper;

import com.wlb.forever.rpc.client.annotation.EnableRpcClients;
import com.wlb.forever.rpc.client.annotation.RpcClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: william
 * @Date: 18/10/22 16:59
 * @Description:
 */
@Slf4j
public class RpcClientsRegistrar implements ImportBeanDefinitionRegistrar,
        ResourceLoaderAware, BeanClassLoaderAware, EnvironmentAware {
    public static Map<Class<?>, RpcProxyFactory<?>> knownRpcClients = new ConcurrentHashMap<>();
    private ResourceLoader resourceLoader;

    private ClassLoader classLoader;

    private Environment environment;

    public RpcClientsRegistrar() {
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        log.info("=====================");
        log.info("开始初始化rpcclientbean");
        log.info("=====================");
        registerRpcClients(annotationMetadata, beanDefinitionRegistry);
    }


    /**
     * 注册RPC客户端请求beans
     *
     * @param metadata
     * @param registry
     */
    public void registerRpcClients(AnnotationMetadata metadata,
                                   BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setResourceLoader(this.resourceLoader);

        Set<String> basePackages;

        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(RpcClient.class);
        scanner.addIncludeFilter(annotationTypeFilter);
        basePackages = getBasePackages(metadata);


        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);
            for (BeanDefinition candidateComponent : candidateComponents) {
                if (candidateComponent instanceof AnnotatedBeanDefinition) {
                    AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                    AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                    Assert.isTrue(annotationMetadata.isInterface(), "@RpcClient 注解只能作用于接口");
                    Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(RpcClient.class.getCanonicalName());
                    registerRpcClient(registry, annotationMetadata, attributes);
                }
            }
        }
    }

    /**
     * 注册RPC客户端请求bean
     *
     * @param registry
     * @param annotationMetadata
     * @param attributes
     */
    private void registerRpcClient(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata, Map<String, Object> attributes) {
        String className = annotationMetadata.getClassName();
        String serviceName = getAttrValue("serviceName", attributes);
        String beanName = getAttrValue("beanName", attributes);
        if (beanName == null || "".equals(beanName)) {
            try {
                beanName = Class.forName(className).getSimpleName();
            } catch (ClassNotFoundException e) {
                log.error("类{}不存在", className);
                return;
            }
            log.error("客户端注册BEANNAME{}", beanName);
        }
        try {
            addRpcClient(serviceName, beanName, Class.forName(className));
        } catch (ClassNotFoundException e) {
            log.error("类" + className + "不存在");
            return;
        }
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(RpcClientFactoryBean.class);
        String name = getClientName(attributes);
        //definition.addPropertyValue("name", name);
        definition.addPropertyValue("rpcClientInterface", className);

        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        String alias = name + "RpcClient";
        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        beanDefinition.setPrimary(true);

        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className, new String[]{alias});
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
        log.info("创建bean(" + className + ")成功," + "别名" + alias);
    }

    private <T> void addRpcClient(String serviceName, String beanName, Class<T> type) {
        if (knownRpcClients.containsKey(type)) {
            log.error("类型" + type + "已存在");
            return;
        }
        boolean loadCompleted = false;
        try {
            this.knownRpcClients.put(type, new RpcProxyFactory(serviceName, beanName, type));
            log.info(String.valueOf(type));
            loadCompleted = true;
        } finally {
            if (!loadCompleted) {
                this.knownRpcClients.remove(type);
            }
        }
    }


    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {

            @Override
            protected boolean isCandidateComponent(
                    AnnotatedBeanDefinition beanDefinition) {
                if (beanDefinition.getMetadata().isIndependent()) {
                    // TODO until SPR-11711 will be resolved
                    if (beanDefinition.getMetadata().isInterface()
                            && beanDefinition.getMetadata()
                            .getInterfaceNames().length == 1
                            && Annotation.class.getName().equals(beanDefinition
                            .getMetadata().getInterfaceNames()[0])) {
                        try {
                            Class<?> target = ClassUtils.forName(beanDefinition.getMetadata().getClassName(), RpcClientsRegistrar.this.classLoader);
                            return !target.isAnnotation();
                        } catch (Exception ex) {
                            log.error("Could not load target class: " + beanDefinition.getMetadata().getClassName(), ex);
                        }
                    }
                    return true;
                }
                return false;

            }
        };
    }

    private String getAttrValue(String key, Map<String, Object> client) {
        if (client == null) {
            return null;
        }
        String value = (String) client.get(key);

        if (StringUtils.hasText(value)) {
            return value;
        }
        return null;
        //throw new IllegalStateException("'" + key + "'必须设置  @" + RpcClient.class.getSimpleName());
    }

    private String getClientName(Map<String, Object> client) {
        if (client == null) {
            return null;
        }
        String value = (String) client.get("value");
        if (!StringUtils.hasText(value)) {
            value = (String) client.get("name");
        }
        if (StringUtils.hasText(value)) {
            return value;
        }
        value = resolve(value);
        if (!StringUtils.hasText(value)) {
            return "";
        }
        return value;
    }


    private String resolve(String value) {
        if (StringUtils.hasText(value)) {
            return this.environment.resolvePlaceholders(value);
        }
        return value;
    }

    protected Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata
                .getAnnotationAttributes(EnableRpcClients.class.getCanonicalName());

        Set<String> basePackages = new HashSet<>();
        for (String pkg : (String[]) attributes.get("value")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (String pkg : (String[]) attributes.get("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (Class<?> clazz : (Class[]) attributes.get("basePackageClasses")) {
            basePackages.add(ClassUtils.getPackageName(clazz));
        }

        if (basePackages.isEmpty()) {
            basePackages.add(
                    ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }
        return basePackages;
    }

}
