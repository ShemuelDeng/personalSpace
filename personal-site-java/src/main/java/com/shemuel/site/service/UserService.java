package com.shemuel.site.service;

import com.shemuel.site.dto.UserRegisterDTO;
import com.shemuel.site.entity.UserProfile;
import com.shemuel.site.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserProfile register(UserRegisterDTO userRegisterDTOu) {
        UserProfile users = new UserProfile();
        users.setUsername(userRegisterDTOu.getUsername());
        users.setEmail(userRegisterDTOu.getEmail());
        // 密码加密
        users.setPasswordHash(PasswordService.hashPassword(userRegisterDTOu.getPassword()));
        users.setCreatedAt(new Date());
        users.setLastLogin(null);

        return userRepository.save(users);
    }

    public Optional<UserProfile> findByIdentifier(String identifier) {
        return userRepository.findByIdentifier(identifier);
    }
    public Optional<UserProfile> findUserByPhone(String email) {
        return userRepository.findByPhone(email);
    }
    public Optional<UserProfile> findUserByUsername(String email) {
        return userRepository.findByUsername(email);
    }


}
