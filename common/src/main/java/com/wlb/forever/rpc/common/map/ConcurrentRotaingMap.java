package com.wlb.forever.rpc.common.map;

import java.util.Map.Entry;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @Auther: william
 * @Date: 18/11/15 10:28
 * @Description:
 */

public class ConcurrentRotaingMap<K, V> {
    private static final int DEFAULT_NUM_BUCKETS = 3;


    /**
     * 键到期回调接口
     *
     * @param <K>保存键的值
     * @param <V>保存值得类型
     * @author cong
     */
    public static interface ExpiredCallback<K, V> {
        /**
         * @param key 更新的键值
         * @param val 更新的值
         */
        public void expire(K key, V val);
    }


    /**
     * 存放数据的桶
     */
    private ConcurrentLinkedDeque<ConcurrentHashMap<K, V>> _buckets;


    /**
     * 回调方法
     */
    private ExpiredCallback _callback;


    /**
     * 清理线程
     */
    private final Thread _cleaner;


    /**
     * 构造方法
     *
     * @param expirationSecs 键的有效期
     * @param numBuckets     存放的桶的数量
     * @param callback       回调函数
     */
    public ConcurrentRotaingMap(int expirationSecs, int numBuckets,
                                ExpiredCallback<K, V> callback) {
        if (numBuckets < 2) {
            throw new IllegalArgumentException("numBuckets must be >= 2");
        }
        _buckets = new ConcurrentLinkedDeque<ConcurrentHashMap<K, V>>();
        for (int i = 0; i < numBuckets; i++) {
            _buckets.add(new ConcurrentHashMap<K, V>());
        }
        _callback = callback;
        final long expirationMillis = expirationSecs * 1000L;
        final long sleepTime = expirationMillis / (numBuckets - 1);
        _cleaner = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        ConcurrentHashMap<K, V> dead = null;
                        Thread.sleep(sleepTime);
                        rotate();
                    }
                } catch (InterruptedException ex) {
                }
            }
        });
        _cleaner.setDaemon(true);
        _cleaner.start();
    }


    public ConcurrentRotaingMap(int expirationSecs, ExpiredCallback<K, V> callback) {
        this(expirationSecs, DEFAULT_NUM_BUCKETS, callback);
    }


    public ConcurrentRotaingMap(int expirationSecs, int numBuckets) {
        this(expirationSecs, numBuckets, null);
    }


    /**
     * 回调方法
     *
     * @return 返回更新的键的集合
     */
    public ConcurrentHashMap<K, V> rotate() {
        ConcurrentHashMap<K, V> dead = null;
        dead = _buckets.removeLast();
        _buckets.addFirst(new ConcurrentHashMap<K, V>());
        if (_callback != null) {
            for (Entry<K, V> entry : dead.entrySet()) {
                _callback.expire(entry.getKey(), entry.getValue());
            }
        }
        return dead;
    }


    /**
     * @param key 查找键的对象
     * @return 返回的键是否存在
     */
    public boolean containsKey(K key) {
        for (ConcurrentHashMap<K, V> bucket : _buckets) {
            if (bucket.containsKey(key)) {
                return true;
            }
        }
        return false;
    }


    /**
     * @param key 获得的键的对象
     * @return 返回的键的值
     */
    public V get(K key) {


        for (ConcurrentHashMap<K, V> bucket : _buckets) {
            if (bucket.containsKey(key)) {
                return bucket.get(key);
            }
        }
        return null;
    }


    /**
     * @param key   写入的键
     * @param value 写入键的值
     */
    public void put(K key, V value) {
        Iterator<ConcurrentHashMap<K, V>> it = _buckets.iterator();
        ConcurrentHashMap<K, V> bucket = it.next();
        bucket.put(key, value);
        while (it.hasNext()) {
            bucket = it.next();
            bucket.remove(key);
        }
    }


    /**
     * @param key 删除的键
     * @return
     */
    public Object remove(K key) {


        for (ConcurrentHashMap<K, V> bucket : _buckets) {
            if (bucket.containsKey(key)) {
                return bucket.remove(key);
            }
        }
        return null;
    }


    /**
     * 返回键的总数
     *
     * @return
     */
    public int size() {
        int size = 0;
        for (ConcurrentHashMap<K, V> bucket : _buckets) {
            size += bucket.size();
        }
        return size;
    }
}
