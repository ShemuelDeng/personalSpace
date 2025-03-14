package com.shemuel.site.service;

import cn.dev33.satoken.stp.StpUtil;
import com.shemuel.site.entity.UserProfile;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

/**
 * @Author: dengshaoxiang
 * @Date: 2025-03-13-14:53
 * @Description:
 */
@Service
public class AuthService {

    public boolean login(Function<String, Optional<UserProfile>> getUser, String param, String password){
        Optional<UserProfile> userOptional = getUser.apply(param);
        if (!userOptional.isPresent()) {
            return false;
        }

        UserProfile user = userOptional.get();

        if (!PasswordService.checkPassword(password, user.getPasswordHash())) {
            return false;
        }

        // 登录成功
        StpUtil.login(user.getEmail());

        return true;
    }

}
