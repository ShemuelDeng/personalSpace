package com.shemuel.site.service;

import com.shemuel.site.entity.ThirdPartyPlatformAuthInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 第三方平台认证信息表 服务接口
 */
public interface ThirdPartyPlatformAuthInfoService extends IService<ThirdPartyPlatformAuthInfo> {
    /**
     * 查询第三方平台认证信息表分页列表
     */
    IPage<ThirdPartyPlatformAuthInfo> selectPage(ThirdPartyPlatformAuthInfo thirdPartyPlatformAuthInfo);

    /**
     * 查询第三方平台认证信息表列表
     */
    List<ThirdPartyPlatformAuthInfo> selectList(ThirdPartyPlatformAuthInfo thirdPartyPlatformAuthInfo);

    /**
     * 新增第三方平台认证信息表
     */
    boolean insert(ThirdPartyPlatformAuthInfo thirdPartyPlatformAuthInfo);

    /**
     * 修改第三方平台认证信息表
     */
    boolean update(ThirdPartyPlatformAuthInfo thirdPartyPlatformAuthInfo);

    /**
     * 批量删除第三方平台认证信息表
     */
    boolean deleteByIds(List<Integer> ids);
}
