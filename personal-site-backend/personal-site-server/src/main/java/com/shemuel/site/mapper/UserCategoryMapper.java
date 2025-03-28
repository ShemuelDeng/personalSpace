package com.shemuel.site.mapper;

import com.shemuel.site.entity.UserCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户私有分类 Mapper接口
 */
@Mapper
public interface UserCategoryMapper extends BaseMapper<UserCategory> {
} 