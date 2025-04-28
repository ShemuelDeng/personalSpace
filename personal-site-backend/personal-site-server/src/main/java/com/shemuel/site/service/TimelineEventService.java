package com.shemuel.site.service;

import com.shemuel.site.entity.TimelineEvent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 时间轴大事件表 服务接口
 */
public interface TimelineEventService extends IService<TimelineEvent> {
    /**
     * 查询时间轴大事件表分页列表
     */
    IPage<TimelineEvent> selectPage(TimelineEvent timelineEvent);

    /**
     * 查询时间轴大事件表列表
     */
    List<TimelineEvent> selectList(TimelineEvent timelineEvent);

    /**
     * 新增时间轴大事件表
     */
    boolean insert(TimelineEvent timelineEvent);

    /**
     * 修改时间轴大事件表
     */
    boolean update(TimelineEvent timelineEvent);

    /**
     * 批量删除时间轴大事件表
     */
    boolean deleteByIds(List<Integer> ids);
}
