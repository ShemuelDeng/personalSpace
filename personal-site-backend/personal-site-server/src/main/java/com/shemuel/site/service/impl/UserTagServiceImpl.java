package com.shemuel.site.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.UserTagMapper;
import com.shemuel.site.entity.UserTag;
import com.shemuel.site.service.UserTagService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 用户私有标签库 服务实现类
 */
@Service
@RequiredArgsConstructor
public class UserTagServiceImpl extends ServiceImpl<UserTagMapper, UserTag> implements UserTagService {

    /**
     * 查询用户私有标签库分页列表
     */
    @Override
    public IPage<UserTag> selectPage(UserTag userTag) {
        LambdaQueryWrapper<UserTag> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(userTag.getId() != null, UserTag::getId, userTag.getId());
        wrapper.eq(userTag.getUserId() != null, UserTag::getUserId, userTag.getUserId());
        wrapper.eq(userTag.getName() != null, UserTag::getName, userTag.getName());
        wrapper.eq( UserTag::getTagType, 0);
        wrapper.eq(userTag.getCreatedAt() != null, UserTag::getCreatedAt, userTag.getCreatedAt());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询用户私有标签库列表
     */
    @Override
    public List<UserTag> selectList(UserTag userTag) {
        LambdaQueryWrapper<UserTag> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(userTag.getId() != null, UserTag::getId, userTag.getId());
        wrapper.eq(userTag.getUserId() != null, UserTag::getUserId, userTag.getUserId());
        wrapper.eq(userTag.getName() != null, UserTag::getName, userTag.getName());
        wrapper.eq(userTag.getTagType() != null, UserTag::getTagType, userTag.getTagType());
        wrapper.eq(userTag.getCreatedAt() != null, UserTag::getCreatedAt, userTag.getCreatedAt());
        return list(wrapper);
    }

    /**
     * 新增用户私有标签库
     */
    @Override
    public boolean insert(UserTag userTag) {
        return save(userTag);
    }

    /**
     * 修改用户私有标签库
     */
    @Override
    public boolean update(UserTag userTag) {
        return updateById(userTag);
    }

    /**
     * 批量删除用户私有标签库
     */
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return removeByIds(ids);
    }
}
