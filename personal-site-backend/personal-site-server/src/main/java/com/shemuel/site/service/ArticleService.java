package com.shemuel.site.service;

import com.shemuel.site.dto.SaveArticleDTO;
import com.shemuel.site.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 核心文章数据 服务接口
 */
public interface ArticleService extends IService<Article> {
    /**
     * 查询核心文章数据分页列表
     */
    IPage<Article> selectPage(Article article);

    /**
     * 查询核心文章数据列表
     */
    List<Article> selectList(Article article);

    
    /**
     * 新增核心文章数据（包含标签关联）
     */
    boolean insert(SaveArticleDTO articleDTO);

    /**
     * 修改核心文章数据
     */
    boolean update(Article article);

    /**
     * 批量删除核心文章数据
     */
    boolean deleteByIds(List<Integer> ids);
}
