package com.wlb.forever.rpc.client.refresh;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * @Auther: william
 * @Date: 18/11/18 14:21
 * @Description:
 */
public class StandardBeanLifecycleDecorator implements BeanLifecycleDecorator<ReadWriteLock> {

    private final boolean proxyTargetClass;

    public StandardBeanLifecycleDecorator(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    @Override
    public Object decorateBean(Object bean, Context<ReadWriteLock> context) {
        if (context != null) {
            bean = getDisposalLockProxy(bean, context.getAuxiliary().readLock());
        }
        return bean;
    }

    @Override
    public Context<ReadWriteLock> decorateDestructionCallback(final Runnable callback) {
        if (callback == null) {
            return null;
        }
        final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        return new Context<ReadWriteLock>(new Runnable() {
            @Override
            public void run() {
                Lock lock = readWriteLock.writeLock();
                lock.lock();
                try {
                    callback.run();
                } finally {
                    lock.unlock();
                }
            }
        }, readWriteLock);
    }

    /**
     * Apply a lock (preferably a read lock allowing multiple concurrent access) to the bean. Callers should replace the
     * bean input with the output.
     *
     * @param bean the bean to lock
     * @param lock the lock to apply
     * @return a proxy that locks while its methods are executed
     */
    private Object getDisposalLockProxy(Object bean, final Lock lock) {
        ProxyFactory factory = new ProxyFactory(bean);
        factory.setProxyTargetClass(proxyTargetClass);
        factory.addAdvice(new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                lock.lock();
                try {
                    return invocation.proceed();
                } finally {
                    lock.unlock();
                }
            }
        });
        return factory.getProxy();
    }

}