package com.shemuel.site.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author: dengshaoxiang
 * @Date: 2025-03-11-20:36
 * @Description:
 */
@Data
public class UserLoginDTO {
    @NotEmpty(message = "邮箱不能为空")
    @NotNull(message = "邮箱不能为空")
    private String email;

    @NotEmpty(message = "密码不能为空")
    @NotNull(message = "密码不能为空")
    private String password;
}
