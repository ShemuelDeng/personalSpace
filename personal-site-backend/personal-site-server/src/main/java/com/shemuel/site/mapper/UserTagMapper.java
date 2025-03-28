package com.shemuel.site.mapper;

import com.shemuel.site.entity.UserTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户私有标签库 Mapper接口
 */
@Mapper
public interface UserTagMapper extends BaseMapper<UserTag> {
} 