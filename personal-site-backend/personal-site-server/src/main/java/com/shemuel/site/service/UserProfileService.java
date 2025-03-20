package com.shemuel.site.service;

import com.shemuel.site.entity.UserProfile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 用户个人信息 服务接口
 */
public interface UserProfileService extends IService<UserProfile> {
    /**
     * 查询用户个人信息分页列表
     */
    IPage<UserProfile> selectPage(UserProfile userProfile);

    /**
     * 查询用户个人信息列表
     */
    List<UserProfile> selectList(UserProfile userProfile);

    /**
     * 新增用户个人信息
     */
    boolean insert(UserProfile userProfile);

    /**
     * 修改用户个人信息
     */
    boolean update(UserProfile userProfile);

    /**
     * 批量删除用户个人信息
     */
    boolean deleteByIds(List<Long> ids);
}
