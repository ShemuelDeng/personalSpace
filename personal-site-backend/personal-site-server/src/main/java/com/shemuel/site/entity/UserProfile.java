package com.shemuel.site.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;

@Data
@TableName("user_profile")
@Tag(name = "用户个人信息对象")
public class UserProfile implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(name = "")
    private Long id;

    @Schema(name = "用户名")
    private String userName;

    @Schema(name = "性别")
    private String gender;

    @Schema(name = "出生日期")
    private LocalDateTime birthdate;

    @Schema(name = "邮箱")
    private String email;

    @Schema(name = "手机号")
    private String phone;

    @Schema(name = "居住地")
    private String location;

    @Schema(name = "个人网站")
    private String website;

    @Schema(name = "自我介绍")
    private String aboutMe;

    @Schema(name = "BCrypt哈希结果")
    private String passwordHash;

    @Schema(name = "失败次数")
    private Integer failedAttempts;

    @Schema(name = "锁定时间")
    private LocalDateTime lockedUntil;

    @Schema(name = "创建时间")
    private LocalDateTime createdAt;

    @Schema(name = "最后登录时间")
    private LocalDateTime lastLogin;

    @Schema(name = "更新时间")
    private LocalDateTime updatedAt;

    @Schema(name = " 用户状态0: 正常, 1: 锁定, 2: 禁用")
    private Integer userStatus;

    @Schema(name = "注册来源: system/wechat")
    private String sourceType;

    @Schema(name = "微信头像")
    private String avatarUrl;

    @Schema(name = "微信昵称")
    private String nickname;

    @Schema(name = "微信唯一标识")
    private String openid;

    @Schema(name = "微信开放平台统一标识")
    private String unionid;
}
