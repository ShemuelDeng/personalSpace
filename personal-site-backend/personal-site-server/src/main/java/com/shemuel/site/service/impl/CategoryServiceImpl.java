package com.shemuel.site.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.CategoryMapper;
import com.shemuel.site.entity.Category;
import com.shemuel.site.service.CategoryService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 文章分类表 服务实现类
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    /**
     * 查询文章分类表分页列表
     */
    @Override
    public IPage<Category> selectPage(Category category) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(category.getId() != null, Category::getId, category.getId());
        wrapper.eq(category.getName() != null, Category::getName, category.getName());
        wrapper.eq(category.getDescription() != null, Category::getDescription, category.getDescription());
        wrapper.eq(category.getParentId() != null, Category::getParentId, category.getParentId());
        wrapper.eq(category.getCreatedAt() != null, Category::getCreatedAt, category.getCreatedAt());
        wrapper.eq(category.getUpdatedAt() != null, Category::getUpdatedAt, category.getUpdatedAt());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询文章分类表列表
     */
    @Override
    public List<Category> selectList(Category category) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(category.getId() != null, Category::getId, category.getId());
        wrapper.eq(category.getName() != null, Category::getName, category.getName());
        wrapper.eq(category.getDescription() != null, Category::getDescription, category.getDescription());
        wrapper.eq(category.getParentId() != null, Category::getParentId, category.getParentId());
        wrapper.eq(category.getCreatedAt() != null, Category::getCreatedAt, category.getCreatedAt());
        wrapper.eq(category.getUpdatedAt() != null, Category::getUpdatedAt, category.getUpdatedAt());
        return list(wrapper);
    }

    /**
     * 新增文章分类表
     */
    @Override
    public boolean insert(Category category) {
        return save(category);
    }

    /**
     * 修改文章分类表
     */
    @Override
    public boolean update(Category category) {
        return updateById(category);
    }

    /**
     * 批量删除文章分类表
     */
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return removeByIds(ids);
    }
}
