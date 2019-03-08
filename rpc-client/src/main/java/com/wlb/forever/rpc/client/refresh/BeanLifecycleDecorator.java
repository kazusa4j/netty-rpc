package com.wlb.forever.rpc.client.refresh;

/**
 * @Auther: william
 * @Date: 18/11/18 14:21
 * @Description:
 */
public interface BeanLifecycleDecorator<T> {

    /**
     * Optionally decorate and provide a new instance of a compatible bean for
     * the caller to use instead of the input.
     *
     * @param bean
     *            the bean to optionally decorate
     * @param context
     *            the context as created by
     *            {@link #decorateDestructionCallback(Runnable)}
     * @return the replacement bean for the caller to use
     */
    Object decorateBean(Object bean, Context<T> context);

    /**
     * Optionally decorate the destruction callback provided, and also return
     * some context that can be used later by the
     * {@link #decorateBean(Object, Context)} method.
     *
     * @param callback
     *            the destruction callback that will be used by the container
     * @return a context wrapper
     */
    Context<T> decorateDestructionCallback(Runnable callback);

    static class Context<T> {

        private final T auxiliary;
        private final Runnable callback;

        public Context(Runnable callback, T auxiliary) {
            this.callback = callback;
            this.auxiliary = auxiliary;
        }

        public Runnable getCallback() {
            return callback;
        }

        public T getAuxiliary() {
            return auxiliary;
        }

    }

}