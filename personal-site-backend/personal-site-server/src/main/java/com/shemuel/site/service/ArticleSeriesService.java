package com.shemuel.site.service;

import com.shemuel.site.entity.ArticleSeries;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 文章专题系列 服务接口
 */
public interface ArticleSeriesService extends IService<ArticleSeries> {
    /**
     * 查询文章专题系列分页列表
     */
    IPage<ArticleSeries> selectPage(ArticleSeries articleSeries);

    /**
     * 查询文章专题系列列表
     */
    List<ArticleSeries> selectList(ArticleSeries articleSeries);

    /**
     * 新增文章专题系列
     */
    boolean insert(ArticleSeries articleSeries);

    /**
     * 修改文章专题系列
     */
    boolean update(ArticleSeries articleSeries);

    /**
     * 批量删除文章专题系列
     */
    boolean deleteByIds(List<Integer> ids);
}
