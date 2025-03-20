package com.shemuel.site.service;

/**
 * @Author: dengshaoxiang
 * @Date: 2025-03-20-16:44
 * @Description:
 */
public interface IRateLimiter {

    boolean isGranted(String userId);

}
