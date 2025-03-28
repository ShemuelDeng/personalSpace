package com.shemuel.site.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.UserCategoryMapper;
import com.shemuel.site.entity.UserCategory;
import com.shemuel.site.service.UserCategoryService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 用户私有分类 服务实现类
 */
@Service
@RequiredArgsConstructor
public class UserCategoryServiceImpl extends ServiceImpl<UserCategoryMapper, UserCategory> implements UserCategoryService {

    /**
     * 查询用户私有分类分页列表
     */
    @Override
    public IPage<UserCategory> selectPage(UserCategory userCategory) {
        LambdaQueryWrapper<UserCategory> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(userCategory.getId() != null, UserCategory::getId, userCategory.getId());
        wrapper.eq(userCategory.getUserId() != null, UserCategory::getUserId, userCategory.getUserId());
        wrapper.eq(userCategory.getName() != null, UserCategory::getName, userCategory.getName());
        wrapper.eq(userCategory.getDescription() != null, UserCategory::getDescription, userCategory.getDescription());
        wrapper.eq(userCategory.getParentId() != null, UserCategory::getParentId, userCategory.getParentId());
        wrapper.eq(userCategory.getCreatedAt() != null, UserCategory::getCreatedAt, userCategory.getCreatedAt());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询用户私有分类列表
     */
    @Override
    public List<UserCategory> selectList(UserCategory userCategory) {
        LambdaQueryWrapper<UserCategory> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(userCategory.getId() != null, UserCategory::getId, userCategory.getId());
        wrapper.eq(userCategory.getUserId() != null, UserCategory::getUserId, userCategory.getUserId());
        wrapper.eq(userCategory.getName() != null, UserCategory::getName, userCategory.getName());
        wrapper.eq(userCategory.getDescription() != null, UserCategory::getDescription, userCategory.getDescription());
        wrapper.eq(userCategory.getParentId() != null, UserCategory::getParentId, userCategory.getParentId());
        wrapper.eq(userCategory.getCreatedAt() != null, UserCategory::getCreatedAt, userCategory.getCreatedAt());
        return list(wrapper);
    }

    /**
     * 新增用户私有分类
     */
    @Override
    public boolean insert(UserCategory userCategory) {
        return save(userCategory);
    }

    /**
     * 修改用户私有分类
     */
    @Override
    public boolean update(UserCategory userCategory) {
        return updateById(userCategory);
    }

    /**
     * 批量删除用户私有分类
     */
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return removeByIds(ids);
    }
}
