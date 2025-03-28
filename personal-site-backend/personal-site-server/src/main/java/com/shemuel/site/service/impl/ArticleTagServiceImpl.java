package com.shemuel.site.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.ArticleTagMapper;
import com.shemuel.site.entity.ArticleTagRelation;
import com.shemuel.site.service.ArticleTagService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 文章标签关联表 服务实现类
 */
@Service
@RequiredArgsConstructor
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTagRelation> implements ArticleTagService {

    /**
     * 查询文章标签关联表分页列表
     */
    @Override
    public IPage<ArticleTagRelation> selectPage(ArticleTagRelation articleTagRelation) {
        LambdaQueryWrapper<ArticleTagRelation> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(articleTagRelation.getArticleId() != null, ArticleTagRelation::getArticleId, articleTagRelation.getArticleId());
        wrapper.eq(articleTagRelation.getTagId() != null, ArticleTagRelation::getTagId, articleTagRelation.getTagId());
        wrapper.eq(articleTagRelation.getCreatedAt() != null, ArticleTagRelation::getCreatedAt, articleTagRelation.getCreatedAt());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询文章标签关联表列表
     */
    @Override
    public List<ArticleTagRelation> selectList(ArticleTagRelation articleTagRelation) {
        LambdaQueryWrapper<ArticleTagRelation> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(articleTagRelation.getArticleId() != null, ArticleTagRelation::getArticleId, articleTagRelation.getArticleId());
        wrapper.eq(articleTagRelation.getTagId() != null, ArticleTagRelation::getTagId, articleTagRelation.getTagId());
        wrapper.eq(articleTagRelation.getCreatedAt() != null, ArticleTagRelation::getCreatedAt, articleTagRelation.getCreatedAt());
        return list(wrapper);
    }

    /**
     * 新增文章标签关联表
     */
    @Override
    public boolean insert(ArticleTagRelation articleTagRelation) {
        return save(articleTagRelation);
    }

    /**
     * 修改文章标签关联表
     */
    @Override
    public boolean update(ArticleTagRelation articleTagRelation) {
        return updateById(articleTagRelation);
    }

    /**
     * 批量删除文章标签关联表
     */
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return removeByIds(ids);
    }
}
