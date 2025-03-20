package com.shemuel.site.controller;

import com.shemuel.site.annotation.AccessLimit;
import com.shemuel.site.common.RestResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: dengshaoxiang
 * @Date: 2025-03-11-20:26
 * @Description:
 */
@RestController
@AccessLimit
public class HealthyController {

    @GetMapping("/ok")
    public RestResult<String> healthy() {
        return RestResult.success("Yes, I'm ok!");
    }
}
