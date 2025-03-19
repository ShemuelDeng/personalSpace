package com.shemuel.site.service;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class PasswordService {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    
    // 最终存储的密码哈希
    public static String hashPassword(String password) {
        return encoder.encode(password);
    }
    
    // 验证密码
    public static boolean checkPassword(String orgin, String storedHash) {
        return encoder.matches(orgin, storedHash);
    }


    // 新增辅助方法：将字节数组转换为十六进制字符串
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}