package com.shemuel.site.mapper;

import com.shemuel.site.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章分类表 Mapper接口
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
} 