package com.shemuel.site.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.SocialLinksMapper;
import com.shemuel.site.entity.SocialLinks;
import com.shemuel.site.service.SocialLinksService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 社交连接信息 服务实现类
 */
@Service
@RequiredArgsConstructor
public class SocialLinksServiceImpl extends ServiceImpl<SocialLinksMapper, SocialLinks> implements SocialLinksService {

    /**
     * 查询社交连接信息分页列表
     */
    @Override
    public IPage<SocialLinks> selectPage(SocialLinks socialLinks) {
        LambdaQueryWrapper<SocialLinks> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(socialLinks.getId() != null, SocialLinks::getId, socialLinks.getId());
        wrapper.eq(socialLinks.getUserId() != null, SocialLinks::getUserId, socialLinks.getUserId());
        wrapper.eq(socialLinks.getPlatform() != null, SocialLinks::getPlatform, socialLinks.getPlatform());
        wrapper.eq(socialLinks.getUrl() != null, SocialLinks::getUrl, socialLinks.getUrl());
        wrapper.eq(socialLinks.getSortOrder() != null, SocialLinks::getSortOrder, socialLinks.getSortOrder());
        wrapper.eq(socialLinks.getCreatedAt() != null, SocialLinks::getCreatedAt, socialLinks.getCreatedAt());
        wrapper.eq(socialLinks.getUpdatedAt() != null, SocialLinks::getUpdatedAt, socialLinks.getUpdatedAt());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询社交连接信息列表
     */
    @Override
    public List<SocialLinks> selectList(SocialLinks socialLinks) {
        LambdaQueryWrapper<SocialLinks> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(socialLinks.getId() != null, SocialLinks::getId, socialLinks.getId());
        wrapper.eq(socialLinks.getUserId() != null, SocialLinks::getUserId, socialLinks.getUserId());
        wrapper.eq(socialLinks.getPlatform() != null, SocialLinks::getPlatform, socialLinks.getPlatform());
        wrapper.eq(socialLinks.getUrl() != null, SocialLinks::getUrl, socialLinks.getUrl());
        wrapper.eq(socialLinks.getSortOrder() != null, SocialLinks::getSortOrder, socialLinks.getSortOrder());
        wrapper.eq(socialLinks.getCreatedAt() != null, SocialLinks::getCreatedAt, socialLinks.getCreatedAt());
        wrapper.eq(socialLinks.getUpdatedAt() != null, SocialLinks::getUpdatedAt, socialLinks.getUpdatedAt());
        return list(wrapper);
    }

    /**
     * 新增社交连接信息
     */
    @Override
    public boolean insert(SocialLinks socialLinks) {
        return save(socialLinks);
    }

    /**
     * 修改社交连接信息
     */
    @Override
    public boolean update(SocialLinks socialLinks) {
        return updateById(socialLinks);
    }

    /**
     * 批量删除社交连接信息
     */
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return removeByIds(ids);
    }
}
