package com.shemuel.site.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.ArticleTagRelationMapper;
import com.shemuel.site.entity.ArticleTagRelation;
import com.shemuel.site.service.ArticleTagRelationService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文章标签关联表 服务实现类
 */
@Service
@RequiredArgsConstructor
public class ArticleTagRelationServiceImpl extends ServiceImpl<ArticleTagRelationMapper, ArticleTagRelation> implements ArticleTagRelationService {

    /**
     * 查询文章标签关联表分页列表
     */
    @Override
    public IPage<ArticleTagRelation> selectPage(ArticleTagRelation articleTagRelation) {
        LambdaQueryWrapper<ArticleTagRelation> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(articleTagRelation.getId() != null, ArticleTagRelation::getId, articleTagRelation.getId());
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
        wrapper.eq(articleTagRelation.getId() != null, ArticleTagRelation::getId, articleTagRelation.getId());
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
    
    /**
     * 批量保存文章标签关联
     * @param articleId 文章ID
     * @param tagIds 标签ID列表
     * @return 是否保存成功
     */
    @Override
    @Transactional
    public boolean batchSave(Integer articleId, List<Integer> tagIds) {
        if (articleId == null || tagIds == null || tagIds.isEmpty()) {
            return false;
        }
        
        // 先删除该文章的所有标签关联
        LambdaQueryWrapper<ArticleTagRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTagRelation::getArticleId, articleId);
        remove(wrapper);
        
        // 批量插入新的关联记录
        List<ArticleTagRelation> relationList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        
        for (Integer tagId : tagIds) {
            ArticleTagRelation relation = new ArticleTagRelation();
            relation.setArticleId(articleId);
            relation.setTagId(tagId);
            relation.setCreatedAt(now);
            relationList.add(relation);
        }
        
        return saveBatch(relationList);
    }
}
