package com.shemuel.site.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页跳转
 * @Author: 公众号: 加瓦点灯
 * @Date: 2025-04-11-14:47
 * @Description:
 */
@Controller
public class IndexController {

    @GetMapping(value = {"/", "/{path:[^\\.]*}"})
    public String forward() {
        return "forward:/index.html";
    }

}
