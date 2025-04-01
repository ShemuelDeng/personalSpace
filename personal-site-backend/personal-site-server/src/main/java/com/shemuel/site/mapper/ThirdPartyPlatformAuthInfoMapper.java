package com.shemuel.site.mapper;

import com.shemuel.site.bo.ThirdPartyPlatformWithAuthInfo;
import com.shemuel.site.entity.ThirdPartyPlatformAuthInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 第三方平台认证信息表 Mapper接口
 */
@Mapper
public interface ThirdPartyPlatformAuthInfoMapper extends BaseMapper<ThirdPartyPlatformAuthInfo> {

    ThirdPartyPlatformWithAuthInfo selectByUserId(@Param("uid") Long uid, @Param("platformType") Integer platformType);
} 