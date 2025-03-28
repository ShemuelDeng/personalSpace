package com.shemuel.site.service;

import com.shemuel.site.entity.ArticleTagRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 文章标签关联表 服务接口
 */
public interface ArticleTagService extends IService<ArticleTagRelation> {
    /**
     * 查询文章标签关联表分页列表
     */
    IPage<ArticleTagRelation> selectPage(ArticleTagRelation articleTagRelation);

    /**
     * 查询文章标签关联表列表
     */
    List<ArticleTagRelation> selectList(ArticleTagRelation articleTagRelation);

    /**
     * 新增文章标签关联表
     */
    boolean insert(ArticleTagRelation articleTagRelation);

    /**
     * 修改文章标签关联表
     */
    boolean update(ArticleTagRelation articleTagRelation);

    /**
     * 批量删除文章标签关联表
     */
    boolean deleteByIds(List<Integer> ids);
}
