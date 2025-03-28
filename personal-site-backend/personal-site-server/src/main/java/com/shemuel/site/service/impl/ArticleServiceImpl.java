package com.shemuel.site.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.ArticleMapper;
import com.shemuel.site.entity.Article;
import com.shemuel.site.service.ArticleService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 核心文章数据 服务实现类
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    /**
     * 查询核心文章数据分页列表
     */
    @Override
    public IPage<Article> selectPage(Article article) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(article.getId() != null, Article::getId, article.getId());
        wrapper.eq(article.getUserId() != null, Article::getUserId, article.getUserId());
        wrapper.eq(article.getCategoryId() != null, Article::getCategoryId, article.getCategoryId());
        wrapper.eq(article.getSeriesId() != null, Article::getSeriesId, article.getSeriesId());
        wrapper.eq(article.getTitle() != null, Article::getTitle, article.getTitle());
        wrapper.eq(article.getSummary() != null, Article::getSummary, article.getSummary());
        wrapper.eq(article.getContent() != null, Article::getContent, article.getContent());
        wrapper.eq(article.getCoverImage() != null, Article::getCoverImage, article.getCoverImage());
        wrapper.eq(article.getStatus() != null, Article::getStatus, article.getStatus());
        wrapper.eq(article.getViewCount() != null, Article::getViewCount, article.getViewCount());
        wrapper.eq(article.getLikeCount() != null, Article::getLikeCount, article.getLikeCount());
        wrapper.eq(article.getCreatedAt() != null, Article::getCreatedAt, article.getCreatedAt());
        wrapper.eq(article.getUpdatedAt() != null, Article::getUpdatedAt, article.getUpdatedAt());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询核心文章数据列表
     */
    @Override
    public List<Article> selectList(Article article) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(article.getId() != null, Article::getId, article.getId());
        wrapper.eq(article.getUserId() != null, Article::getUserId, article.getUserId());
        wrapper.eq(article.getCategoryId() != null, Article::getCategoryId, article.getCategoryId());
        wrapper.eq(article.getSeriesId() != null, Article::getSeriesId, article.getSeriesId());
        wrapper.eq(article.getTitle() != null, Article::getTitle, article.getTitle());
        wrapper.eq(article.getSummary() != null, Article::getSummary, article.getSummary());
        wrapper.eq(article.getContent() != null, Article::getContent, article.getContent());
        wrapper.eq(article.getCoverImage() != null, Article::getCoverImage, article.getCoverImage());
        wrapper.eq(article.getStatus() != null, Article::getStatus, article.getStatus());
        wrapper.eq(article.getViewCount() != null, Article::getViewCount, article.getViewCount());
        wrapper.eq(article.getLikeCount() != null, Article::getLikeCount, article.getLikeCount());
        wrapper.eq(article.getCreatedAt() != null, Article::getCreatedAt, article.getCreatedAt());
        wrapper.eq(article.getUpdatedAt() != null, Article::getUpdatedAt, article.getUpdatedAt());
        return list(wrapper);
    }

    /**
     * 新增核心文章数据
     */
    @Override
    public boolean insert(Article article) {
        return save(article);
    }

    /**
     * 修改核心文章数据
     */
    @Override
    public boolean update(Article article) {
        return updateById(article);
    }

    /**
     * 批量删除核心文章数据
     */
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return removeByIds(ids);
    }
}
