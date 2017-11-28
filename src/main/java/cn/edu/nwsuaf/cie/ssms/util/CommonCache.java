package cn.edu.nwsuaf.cie.ssms.util;

import javax.annotation.PreDestroy;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangrenjie on 2017-11-28
 */
public class CommonCache {

    /**
     * ConcurrentHashMap 用来当缓存用
     */
    private static final ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();
    /**
     * 缓存过期相当于从 map 中删除对应的数据，可以通过定时任务来实现
     */
    private static final ScheduledExecutorService timerTask = Executors.newScheduledThreadPool(2);

    public void put(String key, Object value) {
        cache.put(key, value);
    }

    /**
     * 添加一个定时过期的缓存
     *
     * @param key
     * @param value
     * @param delay 有效时间
     * @param unit  时间单位
     */
    public void putEx(String key, Object value, long delay, TimeUnit unit) {
        put(key, value);
        timerTask.schedule(new RemoveTask(key), delay, unit);
    }

    public Object get(String key) {
        return cache.get("key");
    }

    public void remove(String key) {
        cache.remove(key);
    }

    /**
     * PreDestroy 注解相当于注册了 ShutdownHook
     * 退出时关闭定时任务
     */
    @PreDestroy
    private void stop() {
        timerTask.shutdown();
    }

    /**
     * 定时任务，根据构造函数传入的 key 删除对应的数据
     */
    private class RemoveTask implements Runnable {

        private String key;

        public RemoveTask(String key) {
            this.key = key;
        }

        @Override
        public void run() {
            cache.remove(key);
        }
    }

}
