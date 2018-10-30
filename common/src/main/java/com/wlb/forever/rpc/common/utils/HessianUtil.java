package com.wlb.forever.rpc.common.utils;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Auther: william
 * @Date: 18/10/30 13:38
 * @Description:HESSIAN工具类
 */
public class HessianUtil {
    /**
     * 序列化
     * @param obj
     * @return
     */
    public static byte[] serializer(Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Hessian2Output hOut = new Hessian2Output(out);
        byte[] bytes = null;
        try {
            hOut.writeObject(obj);
            hOut.flush();
            bytes = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                hOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    /**
     * 反序列化
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    public static <T> T deserialize(Class<T> clazz, byte[] bytes) {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        Hessian2Input hIn = new Hessian2Input(in);
        T deVo = null;
        try {
            deVo = (T) hIn.readObject();
            hIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deVo;
    }
}
