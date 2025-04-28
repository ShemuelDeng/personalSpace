package com.shemuel.site.mapper;

import com.shemuel.site.entity.TimelineEvent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 时间轴大事件表 Mapper接口
 */
@Mapper
public interface TimelineEventMapper extends BaseMapper<TimelineEvent> {
} 