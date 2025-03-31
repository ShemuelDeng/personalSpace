package com.shemuel.site.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.ThirdPartyPlatformMapper;
import com.shemuel.site.entity.ThirdPartyPlatform;
import com.shemuel.site.service.ThirdPartyPlatformService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 第三方平台信息表 服务实现类
 */
@Service
@RequiredArgsConstructor
public class ThirdPartyPlatformServiceImpl extends ServiceImpl<ThirdPartyPlatformMapper, ThirdPartyPlatform> implements ThirdPartyPlatformService {

    /**
     * 查询第三方平台信息表分页列表
     */
    @Override
    public IPage<ThirdPartyPlatform> selectPage(ThirdPartyPlatform thirdPartyPlatform) {
        LambdaQueryWrapper<ThirdPartyPlatform> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(thirdPartyPlatform.getId() != null, ThirdPartyPlatform::getId, thirdPartyPlatform.getId());
        wrapper.eq(thirdPartyPlatform.getPlatformType() != null, ThirdPartyPlatform::getPlatformType, thirdPartyPlatform.getPlatformType());
        wrapper.eq(thirdPartyPlatform.getPlatformName() != null, ThirdPartyPlatform::getPlatformName, thirdPartyPlatform.getPlatformName());
        wrapper.eq(thirdPartyPlatform.getCreateDraftUrl() != null, ThirdPartyPlatform::getCreateDraftUrl, thirdPartyPlatform.getCreateDraftUrl());
        wrapper.eq(thirdPartyPlatform.getUpdateDraftUrl() != null, ThirdPartyPlatform::getUpdateDraftUrl, thirdPartyPlatform.getUpdateDraftUrl());
        wrapper.eq(thirdPartyPlatform.getSetTopicUrl() != null, ThirdPartyPlatform::getSetTopicUrl, thirdPartyPlatform.getSetTopicUrl());
        wrapper.eq(thirdPartyPlatform.getPublishArticleUrl() != null, ThirdPartyPlatform::getPublishArticleUrl, thirdPartyPlatform.getPublishArticleUrl());
        wrapper.eq(thirdPartyPlatform.getHeader() != null, ThirdPartyPlatform::getHeader, thirdPartyPlatform.getHeader());
        wrapper.eq(thirdPartyPlatform.getCookie() != null, ThirdPartyPlatform::getCookie, thirdPartyPlatform.getCookie());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询第三方平台信息表列表
     */
    @Override
    public List<ThirdPartyPlatform> selectList(ThirdPartyPlatform thirdPartyPlatform) {
        LambdaQueryWrapper<ThirdPartyPlatform> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(thirdPartyPlatform.getId() != null, ThirdPartyPlatform::getId, thirdPartyPlatform.getId());
        wrapper.eq(thirdPartyPlatform.getPlatformType() != null, ThirdPartyPlatform::getPlatformType, thirdPartyPlatform.getPlatformType());
        wrapper.eq(thirdPartyPlatform.getPlatformName() != null, ThirdPartyPlatform::getPlatformName, thirdPartyPlatform.getPlatformName());
        wrapper.eq(thirdPartyPlatform.getCreateDraftUrl() != null, ThirdPartyPlatform::getCreateDraftUrl, thirdPartyPlatform.getCreateDraftUrl());
        wrapper.eq(thirdPartyPlatform.getUpdateDraftUrl() != null, ThirdPartyPlatform::getUpdateDraftUrl, thirdPartyPlatform.getUpdateDraftUrl());
        wrapper.eq(thirdPartyPlatform.getSetTopicUrl() != null, ThirdPartyPlatform::getSetTopicUrl, thirdPartyPlatform.getSetTopicUrl());
        wrapper.eq(thirdPartyPlatform.getPublishArticleUrl() != null, ThirdPartyPlatform::getPublishArticleUrl, thirdPartyPlatform.getPublishArticleUrl());
        wrapper.eq(thirdPartyPlatform.getHeader() != null, ThirdPartyPlatform::getHeader, thirdPartyPlatform.getHeader());
        wrapper.eq(thirdPartyPlatform.getCookie() != null, ThirdPartyPlatform::getCookie, thirdPartyPlatform.getCookie());
        return list(wrapper);
    }

    /**
     * 新增第三方平台信息表
     */
    @Override
    public boolean insert(ThirdPartyPlatform thirdPartyPlatform) {
        return save(thirdPartyPlatform);
    }

    /**
     * 修改第三方平台信息表
     */
    @Override
    public boolean update(ThirdPartyPlatform thirdPartyPlatform) {
        return updateById(thirdPartyPlatform);
    }

    /**
     * 批量删除第三方平台信息表
     */
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return removeByIds(ids);
    }
}
