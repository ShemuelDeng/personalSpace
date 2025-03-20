package com.shemuel.site.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.util.PhoneUtil;
import com.shemuel.site.annotation.AccessLimit;
import com.shemuel.site.common.RestResult;
import com.shemuel.site.dto.UserLoginDTO;
import com.shemuel.site.dto.UserPasswordResetDTO;
import com.shemuel.site.dto.UserRegisterDTO;
import com.shemuel.site.entity.UserProfile;
import com.shemuel.site.exception.BusinessException;
import com.shemuel.site.service.AuthService;
import com.shemuel.site.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: dengshaoxiang
 * @Date: 2025-03-11-20:35
 * @Description:
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    @AccessLimit(key = "#userLoginDTO.identifier", interval = 5)
    public RestResult<SaTokenInfo> login(@RequestBody @Validated UserLoginDTO userLoginDTO) {

        if (!authService.login(userLoginDTO.getIdentifier(), userLoginDTO.getPassword())){
            return RestResult.error("用户名或密码错误");
        }

        // 登录成功
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return RestResult.success(tokenInfo);
    }


    @PostMapping("/register")
    public SaResult register(@RequestBody UserRegisterDTO registerDTO) {

        if (StringUtils.isNotEmpty(registerDTO.getPhone()) && !PhoneUtil.isPhone(registerDTO.getPhone())){
            throw new BusinessException(500, "手机号格式错误");
        }

        if (StringUtils.isNotEmpty(registerDTO.getEmail()) && !registerDTO.getEmail().contains("@")){
            throw new BusinessException(500, "邮箱格式错误");
        }
        userService.register(registerDTO);
        // 这里需要添加数据库保存逻辑
        return SaResult.data("注册成功");
    }




    @PostMapping("/password/recall")
    public SaResult resetPassword(@RequestBody @Validated UserPasswordResetDTO userLoginDTO) {
        UserProfile userProfile = authService.checkCaptcha(userLoginDTO.getIdentifier(), userLoginDTO.getCaptcha());
        authService.updatePassword(userProfile, userLoginDTO.getNewPassword());
        return SaResult.data("ok");
    }
}
