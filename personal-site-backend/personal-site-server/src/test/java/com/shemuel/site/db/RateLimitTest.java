package com.shemuel.site.db;

import com.shemuel.site.common.LocalRateLimiter;
import org.junit.jupiter.api.Test;

/**
 * @Author: 公众号: 加瓦点灯
 * @Date: 2025-03-20-16:43
 * @Description:
 */
public class RateLimitTest {
    @Test
    public void test() throws Exception{
        LocalRateLimiter limiter = new LocalRateLimiter(1, 10);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 53; i++) {
            if (!limiter.isGranted("test")) {
                System.out.println("1 too frequency " + i);
            }
        }
        Thread.sleep(1 * 1000);
        System.out.println("sleep 1 s");
        for (int i = 0; i < 53; i++) {
            if (!limiter.isGranted("test")) {
                System.out.println("2 too frequency " + i);
            }
        }

        Thread.sleep(5 * 1000);
        System.out.println("sleep 5 s");
        for (int i = 0; i < 53; i++) {
            if (!limiter.isGranted("test")) {
                System.out.println("3 too frequency " + i);
            }
        }

        Thread.sleep(5 * 1000);
        System.out.println("sleep 5 s");
        long second = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            if (!limiter.isGranted("test")) {
                System.out.println("4 too frequency " + i);
            }
            Thread.sleep(50);
        }
        System.out.println("second: " + (System.currentTimeMillis() - second));
        System.out.println("end: " + (System.currentTimeMillis() - start));
    }
}
