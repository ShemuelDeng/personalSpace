package com.shemuel.site.service.impl;

import java.util.List;

import com.shemuel.site.entity.ArticleTag;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.TagMapper;
import com.shemuel.site.service.TagService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 文章标签表 服务实现类
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper, ArticleTag> implements TagService {

    /**
     * 查询文章标签表分页列表
     */
    @Override
    public IPage<ArticleTag> selectPage(ArticleTag articleTag) {
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(articleTag.getId() != null, ArticleTag::getId, articleTag.getId());
        wrapper.eq(articleTag.getName() != null, ArticleTag::getName, articleTag.getName());
        wrapper.eq(articleTag.getCreatedAt() != null, ArticleTag::getCreatedAt, articleTag.getCreatedAt());
        wrapper.eq(articleTag.getUpdatedAt() != null, ArticleTag::getUpdatedAt, articleTag.getUpdatedAt());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询文章标签表列表
     */
    @Override
    public List<ArticleTag> selectList(ArticleTag articleTag) {
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(articleTag.getId() != null, ArticleTag::getId, articleTag.getId());
        wrapper.eq(articleTag.getName() != null, ArticleTag::getName, articleTag.getName());
        wrapper.eq(articleTag.getCreatedAt() != null, ArticleTag::getCreatedAt, articleTag.getCreatedAt());
        wrapper.eq(articleTag.getUpdatedAt() != null, ArticleTag::getUpdatedAt, articleTag.getUpdatedAt());
        return list(wrapper);
    }

    /**
     * 新增文章标签表
     */
    @Override
    public boolean insert(ArticleTag articleTag) {
        return save(articleTag);
    }

    /**
     * 修改文章标签表
     */
    @Override
    public boolean update(ArticleTag articleTag) {
        return updateById(articleTag);
    }

    /**
     * 批量删除文章标签表
     */
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return removeByIds(ids);
    }
}
