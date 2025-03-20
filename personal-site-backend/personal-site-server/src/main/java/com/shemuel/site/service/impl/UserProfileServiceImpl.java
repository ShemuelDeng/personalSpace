package com.shemuel.site.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.UserProfileMapper;
import com.shemuel.site.entity.UserProfile;
import com.shemuel.site.service.UserProfileService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 用户个人信息 服务实现类
 */
@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl extends ServiceImpl<UserProfileMapper, UserProfile> implements UserProfileService {

    /**
     * 查询用户个人信息分页列表
     */
    @Override
    public IPage<UserProfile> selectPage(UserProfile userProfile) {
        LambdaQueryWrapper<UserProfile> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(userProfile.getId() != null, UserProfile::getId, userProfile.getId());
        wrapper.eq(userProfile.getUserName() != null, UserProfile::getUserName, userProfile.getUserName());
        wrapper.eq(userProfile.getGender() != null, UserProfile::getGender, userProfile.getGender());
        wrapper.eq(userProfile.getBirthdate() != null, UserProfile::getBirthdate, userProfile.getBirthdate());
        wrapper.eq(userProfile.getEmail() != null, UserProfile::getEmail, userProfile.getEmail());
        wrapper.eq(userProfile.getPhone() != null, UserProfile::getPhone, userProfile.getPhone());
        wrapper.eq(userProfile.getLocation() != null, UserProfile::getLocation, userProfile.getLocation());
        wrapper.eq(userProfile.getWebsite() != null, UserProfile::getWebsite, userProfile.getWebsite());
        wrapper.eq(userProfile.getAboutMe() != null, UserProfile::getAboutMe, userProfile.getAboutMe());
        wrapper.eq(userProfile.getPasswordHash() != null, UserProfile::getPasswordHash, userProfile.getPasswordHash());
        wrapper.eq(userProfile.getFailedAttempts() != null, UserProfile::getFailedAttempts, userProfile.getFailedAttempts());
        wrapper.eq(userProfile.getLockedUntil() != null, UserProfile::getLockedUntil, userProfile.getLockedUntil());
        wrapper.eq(userProfile.getCreatedAt() != null, UserProfile::getCreatedAt, userProfile.getCreatedAt());
        wrapper.eq(userProfile.getLastLogin() != null, UserProfile::getLastLogin, userProfile.getLastLogin());
        wrapper.eq(userProfile.getUpdatedAt() != null, UserProfile::getUpdatedAt, userProfile.getUpdatedAt());
        wrapper.eq(userProfile.getUserStatus() != null, UserProfile::getUserStatus, userProfile.getUserStatus());
        wrapper.eq(userProfile.getSourceType() != null, UserProfile::getSourceType, userProfile.getSourceType());
        wrapper.eq(userProfile.getAvatarUrl() != null, UserProfile::getAvatarUrl, userProfile.getAvatarUrl());
        wrapper.eq(userProfile.getNickname() != null, UserProfile::getNickname, userProfile.getNickname());
        wrapper.eq(userProfile.getOpenid() != null, UserProfile::getOpenid, userProfile.getOpenid());
        wrapper.eq(userProfile.getUnionid() != null, UserProfile::getUnionid, userProfile.getUnionid());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询用户个人信息列表
     */
    @Override
    public List<UserProfile> selectList(UserProfile userProfile) {
        LambdaQueryWrapper<UserProfile> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(userProfile.getId() != null, UserProfile::getId, userProfile.getId());
        wrapper.eq(userProfile.getUserName() != null, UserProfile::getUserName, userProfile.getUserName());
        wrapper.eq(userProfile.getGender() != null, UserProfile::getGender, userProfile.getGender());
        wrapper.eq(userProfile.getBirthdate() != null, UserProfile::getBirthdate, userProfile.getBirthdate());
        wrapper.eq(userProfile.getEmail() != null, UserProfile::getEmail, userProfile.getEmail());
        wrapper.eq(userProfile.getPhone() != null, UserProfile::getPhone, userProfile.getPhone());
        wrapper.eq(userProfile.getLocation() != null, UserProfile::getLocation, userProfile.getLocation());
        wrapper.eq(userProfile.getWebsite() != null, UserProfile::getWebsite, userProfile.getWebsite());
        wrapper.eq(userProfile.getAboutMe() != null, UserProfile::getAboutMe, userProfile.getAboutMe());
        wrapper.eq(userProfile.getPasswordHash() != null, UserProfile::getPasswordHash, userProfile.getPasswordHash());
        wrapper.eq(userProfile.getFailedAttempts() != null, UserProfile::getFailedAttempts, userProfile.getFailedAttempts());
        wrapper.eq(userProfile.getLockedUntil() != null, UserProfile::getLockedUntil, userProfile.getLockedUntil());
        wrapper.eq(userProfile.getCreatedAt() != null, UserProfile::getCreatedAt, userProfile.getCreatedAt());
        wrapper.eq(userProfile.getLastLogin() != null, UserProfile::getLastLogin, userProfile.getLastLogin());
        wrapper.eq(userProfile.getUpdatedAt() != null, UserProfile::getUpdatedAt, userProfile.getUpdatedAt());
        wrapper.eq(userProfile.getUserStatus() != null, UserProfile::getUserStatus, userProfile.getUserStatus());
        wrapper.eq(userProfile.getSourceType() != null, UserProfile::getSourceType, userProfile.getSourceType());
        wrapper.eq(userProfile.getAvatarUrl() != null, UserProfile::getAvatarUrl, userProfile.getAvatarUrl());
        wrapper.eq(userProfile.getNickname() != null, UserProfile::getNickname, userProfile.getNickname());
        wrapper.eq(userProfile.getOpenid() != null, UserProfile::getOpenid, userProfile.getOpenid());
        wrapper.eq(userProfile.getUnionid() != null, UserProfile::getUnionid, userProfile.getUnionid());
        return list(wrapper);
    }

    /**
     * 新增用户个人信息
     */
    @Override
    public boolean insert(UserProfile userProfile) {
        return save(userProfile);
    }

    /**
     * 修改用户个人信息
     */
    @Override
    public boolean update(UserProfile userProfile) {
        return updateById(userProfile);
    }

    /**
     * 批量删除用户个人信息
     */
    @Override
    public boolean deleteByIds(List<Long> ids) {
        return removeByIds(ids);
    }
}
