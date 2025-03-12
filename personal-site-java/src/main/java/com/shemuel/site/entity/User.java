package com.shemuel.site.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long userId;
    private String username;
    private String email;
    private String phone;
    private String salt;
    private String passwordHash;
    private Date createdAt;
    private Date lastLogin;
    private Boolean enabled;
    private Integer failedAttempts;
    private Date lockedUntil;
}