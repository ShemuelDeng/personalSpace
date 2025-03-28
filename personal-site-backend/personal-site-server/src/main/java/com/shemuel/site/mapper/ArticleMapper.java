package com.shemuel.site.mapper;

import com.shemuel.site.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 核心文章数据 Mapper接口
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
} 