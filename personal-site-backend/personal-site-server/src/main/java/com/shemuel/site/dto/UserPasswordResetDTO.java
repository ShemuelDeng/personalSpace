package com.shemuel.site.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author: dengshaoxiang
 * @Date: 2025-03-11-20:36
 * @Description:
 */
@Data
public class UserPasswordResetDTO {
    @NotEmpty(message = "邮箱不能为空")
    @NotNull(message = "邮箱不能为空")
    @Schema(description = "登录标识, 可选 用户名, 手机号, 邮箱", example = "xxx@qq.com; shemuel;15555888989")
    private String identifier;


    @NotEmpty(message = "验证码不能为空")
    @NotNull(message = "验证码不能为空")
    private String captcha;

    @NotEmpty(message = "新密码不能为空")
    @NotNull(message = "新密码不能为空")
    private String newPassword;
}
