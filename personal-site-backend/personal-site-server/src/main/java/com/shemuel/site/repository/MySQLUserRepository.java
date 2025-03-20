package com.shemuel.site.repository;

import cn.hutool.core.util.PhoneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shemuel.site.entity.UserProfile;
import com.shemuel.site.mapper.UserProfileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author dengsx
 * @create 2025/03/12
 **/
@Repository("mysqlUserRepository")
@ConditionalOnProperty(name = "db.type", havingValue = "mysql")
@Slf4j
public class MySQLUserRepository implements UserRepository {

    @Resource
    private UserProfileMapper userProfileMapper;

    @Override
    public UserProfile save(UserProfile users) {
        userProfileMapper.insert(users);

        return users;
    }

    @Override
    public Optional<UserProfile> findByIdentifier(String identifier) {
        if (PhoneUtil.isPhone(identifier)){
            return findByPhone(identifier);
        }
        LambdaQueryWrapper<UserProfile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserProfile::getEmail, identifier);
        UserProfile userProfile = userProfileMapper.selectOne(queryWrapper);
        return Optional.ofNullable(userProfile);
    }

    @Override
    public Optional<UserProfile> findByPhone(String phone) {
        LambdaQueryWrapper<UserProfile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserProfile::getPhone, phone);
        UserProfile userProfile = userProfileMapper.selectOne(queryWrapper);
        return Optional.ofNullable(userProfile);
    }

    @Override
    public Optional<UserProfile> findByUsername(String username) {
        LambdaQueryWrapper<UserProfile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserProfile::getUserName, username);
        UserProfile users = userProfileMapper.selectOne(queryWrapper);
        return Optional.ofNullable(users);
    }

    @Override
    public int  update(UserProfile users) {
        return userProfileMapper.updateById(users);
    }

    @Override
    public void delete(String userId) {

    }
}
