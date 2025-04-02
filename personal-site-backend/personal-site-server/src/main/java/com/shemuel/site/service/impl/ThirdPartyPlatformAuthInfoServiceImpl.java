package com.shemuel.site.service.impl;

import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.ThirdPartyPlatformAuthInfoMapper;
import com.shemuel.site.entity.ThirdPartyPlatformAuthInfo;
import com.shemuel.site.service.ThirdPartyPlatformAuthInfoService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 第三方平台认证信息表 服务实现类
 */
@Service
@RequiredArgsConstructor
public class ThirdPartyPlatformAuthInfoServiceImpl extends ServiceImpl<ThirdPartyPlatformAuthInfoMapper, ThirdPartyPlatformAuthInfo> implements ThirdPartyPlatformAuthInfoService {

    /**
     * 查询第三方平台认证信息表分页列表
     */
    @Override
    public IPage<ThirdPartyPlatformAuthInfo> selectPage(ThirdPartyPlatformAuthInfo thirdPartyPlatformAuthInfo) {
        LambdaQueryWrapper<ThirdPartyPlatformAuthInfo> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(thirdPartyPlatformAuthInfo.getId() != null, ThirdPartyPlatformAuthInfo::getId, thirdPartyPlatformAuthInfo.getId());
        wrapper.eq(thirdPartyPlatformAuthInfo.getUserId() != null, ThirdPartyPlatformAuthInfo::getUserId, thirdPartyPlatformAuthInfo.getUserId());
        wrapper.eq(thirdPartyPlatformAuthInfo.getPlatformId() != null, ThirdPartyPlatformAuthInfo::getPlatformId, thirdPartyPlatformAuthInfo.getPlatformId());
        wrapper.eq(thirdPartyPlatformAuthInfo.getHeader() != null, ThirdPartyPlatformAuthInfo::getHeader, thirdPartyPlatformAuthInfo.getHeader());
        wrapper.eq(thirdPartyPlatformAuthInfo.getCookie() != null, ThirdPartyPlatformAuthInfo::getCookie, thirdPartyPlatformAuthInfo.getCookie());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询第三方平台认证信息表列表
     */
    @Override
    public List<ThirdPartyPlatformAuthInfo> selectList(ThirdPartyPlatformAuthInfo thirdPartyPlatformAuthInfo) {
        LambdaQueryWrapper<ThirdPartyPlatformAuthInfo> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(thirdPartyPlatformAuthInfo.getId() != null, ThirdPartyPlatformAuthInfo::getId, thirdPartyPlatformAuthInfo.getId());
        wrapper.eq(thirdPartyPlatformAuthInfo.getUserId() != null, ThirdPartyPlatformAuthInfo::getUserId, thirdPartyPlatformAuthInfo.getUserId());
        wrapper.eq(thirdPartyPlatformAuthInfo.getPlatformId() != null, ThirdPartyPlatformAuthInfo::getPlatformId, thirdPartyPlatformAuthInfo.getPlatformId());
        wrapper.eq(thirdPartyPlatformAuthInfo.getHeader() != null, ThirdPartyPlatformAuthInfo::getHeader, thirdPartyPlatformAuthInfo.getHeader());
        wrapper.eq(thirdPartyPlatformAuthInfo.getCookie() != null, ThirdPartyPlatformAuthInfo::getCookie, thirdPartyPlatformAuthInfo.getCookie());
        return list(wrapper);
    }

    /**
     * 新增第三方平台认证信息表
     */
    @Override
    public boolean insert(ThirdPartyPlatformAuthInfo thirdPartyPlatformAuthInfo) {
        return save(thirdPartyPlatformAuthInfo);
    }

    /**
     * 修改第三方平台认证信息表
     */
    @Override
    public boolean update(ThirdPartyPlatformAuthInfo thirdPartyPlatformAuthInfo) {
        if (thirdPartyPlatformAuthInfo == null || thirdPartyPlatformAuthInfo.getPlatformId() == null) {
            return false;
        }

        LambdaUpdateWrapper<ThirdPartyPlatformAuthInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                // 根据 platformId 定位记录
                .eq(ThirdPartyPlatformAuthInfo::getPlatformId, thirdPartyPlatformAuthInfo.getPlatformId())
                // 动态设置非空字段
                .set(StringUtils.isNotEmpty(thirdPartyPlatformAuthInfo.getHeader()), ThirdPartyPlatformAuthInfo::getHeader, thirdPartyPlatformAuthInfo.getHeader())
                .set( StringUtils.isNotEmpty(thirdPartyPlatformAuthInfo.getCookie()), ThirdPartyPlatformAuthInfo::getCookie, thirdPartyPlatformAuthInfo.getCookie());

        return update(updateWrapper);
    }

    /**
     * 批量删除第三方平台认证信息表
     */
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return removeByIds(ids);
    }
}
