package com.shemuel.site.service;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public class PasswordService {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    
    // 生成服务端盐（每个用户唯一）
    public static String generateServerSalt() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    // 最终存储的密码哈希
    public static String hashPassword(String salt) {
        return encoder.encode(salt);
    }
    
    // 验证密码
    public static boolean checkPassword(String salt, String storedHash) {
        return encoder.matches(salt, storedHash);
    }

    // 生成客户端哈希+服务端盐的最终哈希
    public static String doubleHash(String clientHash, String serverSalt) {
        String combined = clientHash + serverSalt;
        return encoder.encode(combined);
    }

    // 验证双重哈希
    public static boolean verifyDoubleHash(String clientHash, String serverSalt, String storedHash) {
        String combined = clientHash + serverSalt;
        return encoder.matches(combined, storedHash);
    }
}