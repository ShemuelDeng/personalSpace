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
    @Schema(description = "")
    private Long id;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "性别")
    private String gender;

    @Schema(description = "出生日期")
    private LocalDateTime birthdate;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "居住地")
    private String location;

    @Schema(description = "个人网站")
    private String website;

    @Schema(description = "自我介绍")
    private String aboutMe;

    @Schema(description = "BCrypt哈希结果")
    private String passwordHash;

    @Schema(description = "失败次数")
    private Integer failedAttempts;

    @Schema(description = "锁定时间")
    private LocalDateTime lockedUntil;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "最后登录时间")
    private LocalDateTime lastLogin;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

    @Schema(description = " 用户状态0: 正常, 1: 锁定, 2: 禁用")
    private Integer userStatus;

    @Schema(description = "注册来源: system/wechat")
    private String sourceType;

    @Schema(description = "微信头像")
    private String avatarUrl;

    @Schema(description = "微信昵称")
    private String nickname;

    @Schema(description = "微信唯一标识")
    private String openid;

    @Schema(description = "微信开放平台统一标识")
    private String unionid;
}
