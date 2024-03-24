package com.ksy.cache;

import com.ksy.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 防止重复写入的锁
 *
 * @author csy
 * @version 1.0.0
 * @time 2024/3/2 14:16
 */
@Component
public class WriteRepeatLockCache {
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 生成KEY的模版
     */
    public static final String keyTemplate = "ai-cv:{%s}:{%s}:{%s}";

    /**
     * 默认redis加锁时间
     */
    public static final int LOCK_TIMEOUT = 60;
    /**
     * 默认redis去除锁时间
     */
    public static final int UNLOCK_TIMEOUT = 2;

    /**
     * 是否已经上锁，如果锁了，直接抛出业务异常
     *
     * @param key 唯一的名称
     * @return
     * @author csy
     * @version 1.0.0
     * @time 2024/3/22 14:17
     */
    private void hasLock(String key) {
        // 如果已经存在，就抛出业务异常，直接进行返回
        if (redisTemplate.hasKey(key)) {
            throw new BusinessException(HttpStatus.LOCKED.value(), "正在处理");


        }
    }

    /**
     * 给接口上锁，默认时间为60秒
     *
     * @param key 唯一的名称
     * @author csy
     * @version 1.0.0
     * @time 2024/3/22 14:17
     */
    private void lock(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        boolean success = valueOperations.setIfAbsent(key, "0", LOCK_TIMEOUT, TimeUnit.SECONDS);
        if (!success) {
            throw new BusinessException(HttpStatus.LOCKED.value(), "正在处理");
        }
    }

    /**
     * 给接口上锁，指定时间
     *
     * @param key 唯一的名称
     * @author csy
     * @version 1.0.0
     * @time 2024/3/22 14:17
     */
    private void lock(String key,Integer second) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        boolean success = valueOperations.setIfAbsent(key, "0", second, TimeUnit.SECONDS);
        if (!success) {
            throw new BusinessException(HttpStatus.LOCKED.value(), "正在处理");
        }
    }

    /**
     * 开锁
     *
     * @param key 唯一的名称
     * @author csy
     * @version 1.0.0
     * @time 2024/3/22 14:17
     */
    private void unlock(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, "0", UNLOCK_TIMEOUT, TimeUnit.SECONDS);
    }

    /**
     * 开锁
     *
     * @param key     唯一的名称
     * @param timeout 过期的时间
     * @author csy
     * @version 1.0.0
     * @time 2024/3/22 14:17
     */
    private void unlock(String key, int timeout) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, "0", UNLOCK_TIMEOUT, TimeUnit.SECONDS);
    }

    /**
     * 开锁
     *
     * @param key     唯一的名称
     * @param timeout 过期的时间
     * @author csy
     * @version 1.0.0
     * @time 2024/3/22 14:17
     */
    private void unlock(String key, int timeout, TimeUnit timeUnit) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, "0", UNLOCK_TIMEOUT, timeUnit);
    }

    /**
     * 直接开锁
     *
     * @param key 唯一的名称
     * @author csy
     * @version 1.0.0
     * @time 2024/3/22 14:17
     */
    public void unlockNow(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 加锁，并返回执行后的结果
     *
     * @param key      锁的值
     * @param unlockTimeout 解锁的时间
     * @param business 具体需要执行的业务
     * @param <R>      返回的值的类型
     * @return
     * @author csy
     * @version 1.0.0
     * @time 2024/3/22 14:17
     */
    public <R> R run(String key, int unlockTimeout, TimeUnit timeUnit, Supplier<R> business) {
        hasLock(key);
        try {
            lock(key);
            return business.get();
        } finally {
            unlock(key, unlockTimeout, timeUnit);
        }
    }

    /**
     * 加锁，并返回执行后的结果
     *
     * @param key      锁的值
     * @param unlockTimeout 解锁的时间
     * @param business 具体需要执行的业务
     * @param <R>      返回的值的类型
     * @return
     * @author csy
     * @version 1.0.0
     * @time 2024/3/22 14:17
     */
    public <R> R run(String key, int unlockTimeout, Supplier<R> business) {
        hasLock(key);
        try {
            lock(key);
            return business.get();
        } finally {
            unlock(key, unlockTimeout);
        }
    }

    /**
     * 加锁，并返回执行后的结果
     *
     * @param key      锁的值
     * @param business 具体需要执行的业务
     * @param unlockNow true: 直接开锁, false:两秒开锁
     * @param <R>      返回的值的类型
     * @return
     * @author csy
     * @version 1.0.0
     * @time 2024/3/22 14:17
     */
    public <R> R run(String key, boolean unlockNow, Supplier<R> business) {
        hasLock(key);
        try {
            lock(key);
            return business.get();
        } finally {
            if (unlockNow) {
                unlockNow(key);
            }else {
                unlock(key);
            }
        }
    }

    /**
     * 加锁，并返回执行后的结果
     *
     * @param key      锁的值
     * @param business 具体需要执行的业务
     * @param <R>      返回的值的类型
     * @return
     * @author csy
     * @version 1.0.0
     * @time 2024/3/22 14:17
     */
    public <R> R run(String key, Supplier<R> business) {
        return run(key, false, business);
    }

    /**
     * 加锁，并返回执行后的结果 (添加自定义过期时间)
     *
     * @param key      锁的值
     * @param business 具体需要执行的业务
     * @param <R>      返回的值的类型
     * @return
     * @author csy
     * @version 1.0.0
     * @time 2024/3/22 14:17
     */
    public <R> R run(String key,Integer second,Boolean unlockNow,Supplier<R> business) {
        hasLock(key);
        try {
            lock(key,second);
            return business.get();
        } finally {
            if (unlockNow) {
                unlockNow(key);
            }
        }
    }

    /**
     * 生成redis锁的KEY
     *
     * @param className  类名称（带包路径的名称）
     * @param methodName 方法名称
     * @param onlyKey    唯一的KEY
     * @return
     * @author csy
     * @version 1.0.0
     * @time 2024/3/22 14:17
     */
    public String getKey(String className, String methodName, Object onlyKey) {
        return String.format(keyTemplate, className, methodName, onlyKey);
    }
}
