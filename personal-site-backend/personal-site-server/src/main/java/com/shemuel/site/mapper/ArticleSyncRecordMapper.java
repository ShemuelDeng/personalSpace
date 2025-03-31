package com.shemuel.site.mapper;

import com.shemuel.site.entity.ArticleSyncRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章同步记录表 Mapper接口
 */
@Mapper
public interface ArticleSyncRecordMapper extends BaseMapper<ArticleSyncRecord> {
} 