package com.shemuel.site.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.shemuel.site.dto.UserLoginDTO;
import com.shemuel.site.dto.UserRegisterDTO;
import com.shemuel.site.entity.User;
import com.shemuel.site.service.PasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: dengshaoxiang
 * @Date: 2025-03-11-20:35
 * @Description:
 */
@RestController
@Slf4j
public class LoginController {

    @PostMapping("/login")
    public SaResult doLogin(@RequestBody @Validated UserLoginDTO userLoginDTO) {
        // 根据邮箱查询用户
        User user = userService.findByEmail(userLoginDTO.getEmail());
        
        if (user == null) {
            return SaResult.error("用户名或密码错误");
        }

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
        String serverSalt = PasswordService.generateServerSalt();
        String finalHash = PasswordService.doubleHash(registerDTO.getClientHash(), serverSalt);
        
        // 创建用户对象并保存到数据库
        User newUser = new User();
        newUser.setEmail(registerDTO.getEmail());
        newUser.setServerSalt(serverSalt);
        newUser.setPasswordHash(finalHash);
        
        // 这里需要添加数据库保存逻辑
        return SaResult.data("注册成功");
    }

}
