package com.shemuel.site.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: dengshaoxiang
 * @Date: 2025-03-11-20:26
 * @Description:
 */
@RestController
public class HealthyController {

    @GetMapping("/AreUOk")
    public String healthy() {
        return "Yes, I'm ok!";
    }
}
