package com.shemuel.site.utils;

import com.shemuel.site.annotation.AccessLimit;
import com.shemuel.site.common.LocalRateLimiter;
import com.shemuel.site.service.IRateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * @Author: dengshaoxiang
 * @Date: 2025-03-20-18:00
 * @Description:
 */
public class RateLimitFactory {

    public static IRateLimiter getRateLimiter(String limiterType, AccessLimit accessLimit) {
       if ("redis".equals(limiterType)){
           throw new SecurityException("redis限流暂未实现");
       }else {
           return new LocalRateLimiter( accessLimit.interval(), accessLimit.limit());
       }
    }
}
