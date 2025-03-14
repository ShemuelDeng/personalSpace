package com.shemuel.site.controller.auth;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.util.PhoneUtil;
import com.shemuel.site.dto.UserLoginDTO;
import com.shemuel.site.dto.UserRegisterDTO;
import com.shemuel.site.exception.BusinessException;
import com.shemuel.site.service.AuthService;
import com.shemuel.site.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    public SaResult login(@RequestBody @Validated UserLoginDTO userLoginDTO) {

        if (!authService.login(userService::findByIdentifier, userLoginDTO.getIdentifier(), userLoginDTO.getPassword())){
            return SaResult.error("用户名或密码错误");
        }
        
        // 登录成功
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return SaResult.data(tokenInfo);
    }


    @PostMapping("/register")
    public SaResult register(@RequestBody UserRegisterDTO registerDTO) {
        if (StringUtils.isNotEmpty(registerDTO.getPhone()) && !PhoneUtil.isPhone(registerDTO.getPhone())){
            throw new BusinessException(500, "手机号格式错误");
        }
        userService.register(registerDTO);
        // 这里需要添加数据库保存逻辑
        return SaResult.data("注册成功");
    }

}
