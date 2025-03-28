package com.shemuel.site.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.ArticleSeriesMapper;
import com.shemuel.site.entity.ArticleSeries;
import com.shemuel.site.service.ArticleSeriesService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 文章专题系列 服务实现类
 */
@Service
@RequiredArgsConstructor
public class ArticleSeriesServiceImpl extends ServiceImpl<ArticleSeriesMapper, ArticleSeries> implements ArticleSeriesService {

    /**
     * 查询文章专题系列分页列表
     */
    @Override
    public IPage<ArticleSeries> selectPage(ArticleSeries articleSeries) {
        LambdaQueryWrapper<ArticleSeries> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(articleSeries.getId() != null, ArticleSeries::getId, articleSeries.getId());
        wrapper.eq(articleSeries.getUserId() != null, ArticleSeries::getUserId, articleSeries.getUserId());
        wrapper.eq(articleSeries.getName() != null, ArticleSeries::getName, articleSeries.getName());
        wrapper.eq(articleSeries.getSlug() != null, ArticleSeries::getSlug, articleSeries.getSlug());
        wrapper.eq(articleSeries.getDescription() != null, ArticleSeries::getDescription, articleSeries.getDescription());
        wrapper.eq(articleSeries.getCoverImage() != null, ArticleSeries::getCoverImage, articleSeries.getCoverImage());
        wrapper.eq(articleSeries.getStatus() != null, ArticleSeries::getStatus, articleSeries.getStatus());
        wrapper.eq(articleSeries.getOrderNum() != null, ArticleSeries::getOrderNum, articleSeries.getOrderNum());
        wrapper.eq(articleSeries.getArticleCount() != null, ArticleSeries::getArticleCount, articleSeries.getArticleCount());
        wrapper.eq(articleSeries.getSubscribers() != null, ArticleSeries::getSubscribers, articleSeries.getSubscribers());
        wrapper.eq(articleSeries.getCreatedAt() != null, ArticleSeries::getCreatedAt, articleSeries.getCreatedAt());
        wrapper.eq(articleSeries.getUpdatedAt() != null, ArticleSeries::getUpdatedAt, articleSeries.getUpdatedAt());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询文章专题系列列表
     */
    @Override
    public List<ArticleSeries> selectList(ArticleSeries articleSeries) {
        LambdaQueryWrapper<ArticleSeries> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(articleSeries.getId() != null, ArticleSeries::getId, articleSeries.getId());
        wrapper.eq(articleSeries.getUserId() != null, ArticleSeries::getUserId, articleSeries.getUserId());
        wrapper.eq(articleSeries.getName() != null, ArticleSeries::getName, articleSeries.getName());
        wrapper.eq(articleSeries.getSlug() != null, ArticleSeries::getSlug, articleSeries.getSlug());
        wrapper.eq(articleSeries.getDescription() != null, ArticleSeries::getDescription, articleSeries.getDescription());
        wrapper.eq(articleSeries.getCoverImage() != null, ArticleSeries::getCoverImage, articleSeries.getCoverImage());
        wrapper.eq(articleSeries.getStatus() != null, ArticleSeries::getStatus, articleSeries.getStatus());
        wrapper.eq(articleSeries.getOrderNum() != null, ArticleSeries::getOrderNum, articleSeries.getOrderNum());
        wrapper.eq(articleSeries.getArticleCount() != null, ArticleSeries::getArticleCount, articleSeries.getArticleCount());
        wrapper.eq(articleSeries.getSubscribers() != null, ArticleSeries::getSubscribers, articleSeries.getSubscribers());
        wrapper.eq(articleSeries.getCreatedAt() != null, ArticleSeries::getCreatedAt, articleSeries.getCreatedAt());
        wrapper.eq(articleSeries.getUpdatedAt() != null, ArticleSeries::getUpdatedAt, articleSeries.getUpdatedAt());
        return list(wrapper);
    }

    /**
     * 新增文章专题系列
     */
    @Override
    public boolean insert(ArticleSeries articleSeries) {
        return save(articleSeries);
    }

    /**
     * 修改文章专题系列
     */
    @Override
    public boolean update(ArticleSeries articleSeries) {
        return updateById(articleSeries);
    }

    /**
     * 批量删除文章专题系列
     */
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return removeByIds(ids);
    }
}
