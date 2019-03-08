package com.wlb.forever.rpc.client.refresh;

import org.springframework.context.ApplicationEvent;

import java.util.Set;

/**
 * @Auther: william
 * @Date: 18/11/17 17:34
 * @Description:
 */
public class EnvironmentChangeEvent extends ApplicationEvent {

    private Set<String> keys;

    public EnvironmentChangeEvent(Set<String> keys) {
        super(keys);
        this.keys = keys;
    }

    /**
     * @return the keys
     */
    public Set<String> getKeys() {
        return keys;
    }

}