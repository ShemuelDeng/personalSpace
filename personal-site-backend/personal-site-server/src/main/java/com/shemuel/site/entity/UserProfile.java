package com.shemuel.site.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.util.Date;

@Data
public class UserProfile {
    @TableId()
    private Long id;

    @TableField(value = "user_name")
    private String username;
    private String gender;
    private Date birthdate;
    private String email;
    private String phone;
    private String location;
    private String avatarUrl;
    private String website;
    private String aboutMe;
    private boolean userStatus;
    private String passwordHash;
    private Integer failedAttempts;
    private Date lockedUntil;
    private Date createdAt;
    private Date lastLogin;
    private Date updatedAt;
}