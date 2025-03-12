package com.shemuel.site.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.shemuel.site.dto.UserLoginDTO;
import com.shemuel.site.dto.UserRegisterDTO;
import com.shemuel.site.entity.User;
import com.shemuel.site.service.PasswordService;
import com.shemuel.site.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @Author: dengshaoxiang
 * @Date: 2025-03-11-20:35
 * @Description:
 */
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public SaResult doLogin(@RequestBody @Validated UserLoginDTO userLoginDTO) {
        // 根据邮箱查询用户
        Optional<User> userOptional = userService.findUserByEmail(userLoginDTO.getEmail());
        
        if (!userOptional.isPresent()) {
            return SaResult.error("用户名或密码错误");
        }

        User user = userOptional.get();

        String finalHashPassword = PasswordService.hashPassword(userLoginDTO.getPassword());
        // 哈希+ 服务端盐
        boolean isValid = PasswordService.checkPassword(user.getSalt(), finalHashPassword);
        
        if (!isValid) {
            return SaResult.error("用户名或密码错误");
        }
        
        // 登录成功
        StpUtil.login(user.getEmail());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return SaResult.data(tokenInfo);
    }

    @PostMapping("/loginByPhone")
    public SaResult doLoginPhone(@RequestBody  UserLoginDTO userLoginDTO) {

        return SaResult.error("登录失败");
    }

    @PostMapping("/loginByEmail")
    public SaResult doLoginEmail(@RequestBody  UserLoginDTO userLoginDTO) {

        return SaResult.error("登录失败");
    }

    @PostMapping("/register")
    public SaResult register(@RequestBody UserRegisterDTO registerDTO) {

        
        // 这里需要添加数据库保存逻辑
        return SaResult.data("注册成功");
    }

}
