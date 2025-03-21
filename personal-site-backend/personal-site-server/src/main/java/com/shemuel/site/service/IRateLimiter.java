package com.shemuel.site.service;

/**
 * @Author: 公众号: 加瓦点灯
 * @Date: 2025-03-20-16:44
 * @Description:
 */
public interface IRateLimiter {

    boolean isGranted(String userId);

}
