package com.shemuel.site.mapper;

import com.shemuel.site.entity.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章标签表 Mapper接口
 */
@Mapper
public interface TagMapper extends BaseMapper<ArticleTag> {
} 