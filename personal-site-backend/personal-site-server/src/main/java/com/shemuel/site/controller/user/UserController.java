package com.shemuel.site.controller.user;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 公众号: 加瓦点灯
 * @Date: 2025-03-12-11:13
 * @Description:
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/getUserInfo")
    public String getUserInfo() {
        return "getUserInfo";
    }

    @PostMapping("/logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }

}
