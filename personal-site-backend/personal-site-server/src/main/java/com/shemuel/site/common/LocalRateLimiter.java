package com.shemuel.site.common;

import com.shemuel.site.service.IRateLimiter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 漏桶算法
 * capacity * 1000是为了更精确, 漏水的小洞更小^~^
 */
public class LocalRateLimiter implements IRateLimiter {
    private static final int DEFAULT_LIMIT_TIME_SECOND = 5;
    private static final int DEFAULT_LIMIT_COUNT = 100;
    private static final long expire = 2 * 60 * 60 * 1000;
    private double rate = (double) DEFAULT_LIMIT_COUNT / (DEFAULT_LIMIT_TIME_SECOND);
    private long capacity = DEFAULT_LIMIT_COUNT * 1000;
    private long lastCleanTime;
    private Map<String, Long> requestCountMap = new HashMap<>();
    private Map<String, Long> requestTimeMap = new HashMap<>();

    private SpinLock lock = new SpinLock();

    public LocalRateLimiter() {

    }

    public LocalRateLimiter(int limitTimeSecond, int limitCount) {
        if (limitTimeSecond <= 0 || limitCount <= 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = limitCount * 1000;
        this.rate = (double) limitCount / limitTimeSecond;
    }

    /**
     * 漏桶算法，https://en.wikipedia.org/wiki/Leaky_bucket
     */
    @Override
    public boolean isGranted(String userId) {
        try {
            lock.lock();
            long current = System.currentTimeMillis();
            cleanUp(current);
            Long lastRequestTime = requestTimeMap.get(userId);
            long count = 0;
            if (lastRequestTime == null) {
                count += 1000;
                requestTimeMap.put(userId, current);
                requestCountMap.put(userId, count);
                return true;
            } else {
                count = requestCountMap.get(userId);
                lastRequestTime = requestTimeMap.get(userId);
                count -= (current - lastRequestTime) * rate;
                count = count > 0 ? count : 0;
                requestTimeMap.put(userId, current);
                if (count < capacity) {
                    count += 1000;
                    requestCountMap.put(userId, count);
                    return true;
                } else {
                    requestCountMap.put(userId, count);
                    return false;
                }
            }
        } finally {
            lock.unLock();
        }
    }

    private void cleanUp(long current) {
        if (current - lastCleanTime > expire) {
            for (Iterator<Map.Entry<String, Long>> it = requestTimeMap.entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, Long> entry = it.next();
                if (entry.getValue() < current - expire) {
                    it.remove();
                    requestCountMap.remove(entry.getKey());
                }
            }
            lastCleanTime = current;
        }
    }
}
