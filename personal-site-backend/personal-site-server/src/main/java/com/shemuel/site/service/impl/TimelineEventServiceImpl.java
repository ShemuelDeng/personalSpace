package com.shemuel.site.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.TimelineEventMapper;
import com.shemuel.site.entity.TimelineEvent;
import com.shemuel.site.service.TimelineEventService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 时间轴大事件表 服务实现类
 */
@Service
@RequiredArgsConstructor
public class TimelineEventServiceImpl extends ServiceImpl<TimelineEventMapper, TimelineEvent> implements TimelineEventService {

    /**
     * 查询时间轴大事件表分页列表
     */
    @Override
    public IPage<TimelineEvent> selectPage(TimelineEvent timelineEvent) {
        LambdaQueryWrapper<TimelineEvent> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(timelineEvent.getId() != null, TimelineEvent::getId, timelineEvent.getId());
        wrapper.eq(timelineEvent.getUserId() != null, TimelineEvent::getUserId, timelineEvent.getUserId());
        wrapper.eq(timelineEvent.getEventDate() != null, TimelineEvent::getEventDate, timelineEvent.getEventDate());
        wrapper.eq(timelineEvent.getEventYear() != null, TimelineEvent::getEventYear, timelineEvent.getEventYear());
        wrapper.eq(timelineEvent.getTitle() != null, TimelineEvent::getTitle, timelineEvent.getTitle());
        wrapper.eq(timelineEvent.getContent() != null, TimelineEvent::getContent, timelineEvent.getContent());
        wrapper.eq(timelineEvent.getLocation() != null, TimelineEvent::getLocation, timelineEvent.getLocation());
        wrapper.eq(timelineEvent.getImages() != null, TimelineEvent::getImages, timelineEvent.getImages());
        wrapper.eq(timelineEvent.getCreatedAt() != null, TimelineEvent::getCreatedAt, timelineEvent.getCreatedAt());
        wrapper.eq(timelineEvent.getUpdatedAt() != null, TimelineEvent::getUpdatedAt, timelineEvent.getUpdatedAt());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询时间轴大事件表列表
     */
    @Override
    public List<TimelineEvent> selectList(TimelineEvent timelineEvent) {
        LambdaQueryWrapper<TimelineEvent> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(timelineEvent.getId() != null, TimelineEvent::getId, timelineEvent.getId());
        wrapper.eq(timelineEvent.getUserId() != null, TimelineEvent::getUserId, timelineEvent.getUserId());
        wrapper.eq(timelineEvent.getEventDate() != null, TimelineEvent::getEventDate, timelineEvent.getEventDate());
        wrapper.eq(timelineEvent.getEventYear() != null, TimelineEvent::getEventYear, timelineEvent.getEventYear());
        wrapper.eq(timelineEvent.getTitle() != null, TimelineEvent::getTitle, timelineEvent.getTitle());
        wrapper.eq(timelineEvent.getContent() != null, TimelineEvent::getContent, timelineEvent.getContent());
        wrapper.eq(timelineEvent.getLocation() != null, TimelineEvent::getLocation, timelineEvent.getLocation());
        wrapper.eq(timelineEvent.getImages() != null, TimelineEvent::getImages, timelineEvent.getImages());
        wrapper.eq(timelineEvent.getCreatedAt() != null, TimelineEvent::getCreatedAt, timelineEvent.getCreatedAt());
        wrapper.eq(timelineEvent.getUpdatedAt() != null, TimelineEvent::getUpdatedAt, timelineEvent.getUpdatedAt());
        return list(wrapper);
    }

    /**
     * 新增时间轴大事件表
     */
    @Override
    public boolean insert(TimelineEvent timelineEvent) {
        timelineEvent.setEventYear(timelineEvent.getEventDate().getYear());
        return save(timelineEvent);
    }

    /**
     * 修改时间轴大事件表
     */
    @Override
    public boolean update(TimelineEvent timelineEvent) {
        return updateById(timelineEvent);
    }

    /**
     * 批量删除时间轴大事件表
     */
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return removeByIds(ids);
    }
}
