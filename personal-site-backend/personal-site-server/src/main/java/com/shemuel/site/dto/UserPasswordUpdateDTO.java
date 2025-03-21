package com.shemuel.site.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author: 公众号: 加瓦点灯
 * @Date: 2025-03-11-20:36
 * @Description:
 */
@Data
public class UserPasswordUpdateDTO {

    @NotEmpty(message = "密码不能为空")
    @NotNull(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "新密码不能为空")
    @NotNull(message = "新密码不能为空")
    private String newPassword;
}
