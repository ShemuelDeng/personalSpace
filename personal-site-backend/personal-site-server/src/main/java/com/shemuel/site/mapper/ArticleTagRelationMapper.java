package com.shemuel.site.mapper;

import com.shemuel.site.entity.ArticleTagRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章标签关联表 Mapper接口
 */
@Mapper
public interface ArticleTagRelationMapper extends BaseMapper<ArticleTagRelation> {
} 