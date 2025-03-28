package com.shemuel.site.service;

import com.shemuel.site.entity.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 文章标签表 服务接口
 */
public interface TagService extends IService<ArticleTag> {
    /**
     * 查询文章标签表分页列表
     */
    IPage<ArticleTag> selectPage(ArticleTag articleTag);

    /**
     * 查询文章标签表列表
     */
    List<ArticleTag> selectList(ArticleTag articleTag);

    /**
     * 新增文章标签表
     */
    boolean insert(ArticleTag articleTag);

    /**
     * 修改文章标签表
     */
    boolean update(ArticleTag articleTag);

    /**
     * 批量删除文章标签表
     */
    boolean deleteByIds(List<Integer> ids);
}
