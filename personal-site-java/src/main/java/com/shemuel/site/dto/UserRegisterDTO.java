package com.shemuel.site.dto;

import lombok.Data;

/**
 * @Author: dengshaoxiang
 * @Date: 2025-03-12-11:10
 * @Description:
 */
@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String email;
    private String phone;
}
