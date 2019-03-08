package com.wlb.forever.rpc.client.refresh;

/**
 * @Auther: william
 * @Date: 18/11/17 17:32
 * @Description:
 */
public abstract interface TextEncryptor
{
    public abstract String encrypt(String paramString);

    public abstract String decrypt(String paramString);
}
