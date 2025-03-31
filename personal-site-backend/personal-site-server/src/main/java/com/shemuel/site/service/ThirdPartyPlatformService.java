package com.shemuel.site.service;

import com.shemuel.site.entity.ThirdPartyPlatform;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 第三方平台信息表 服务接口
 */
public interface ThirdPartyPlatformService extends IService<ThirdPartyPlatform> {
    /**
     * 查询第三方平台信息表分页列表
     */
    IPage<ThirdPartyPlatform> selectPage(ThirdPartyPlatform thirdPartyPlatform);

    /**
     * 查询第三方平台信息表列表
     */
    List<ThirdPartyPlatform> selectList(ThirdPartyPlatform thirdPartyPlatform);

    /**
     * 新增第三方平台信息表
     */
    boolean insert(ThirdPartyPlatform thirdPartyPlatform);

    /**
     * 修改第三方平台信息表
     */
    boolean update(ThirdPartyPlatform thirdPartyPlatform);

    /**
     * 批量删除第三方平台信息表
     */
    boolean deleteByIds(List<Integer> ids);
}
