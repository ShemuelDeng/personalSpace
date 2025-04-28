package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.TimelineEvent;
import com.shemuel.site.service.TimelineEventService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 时间轴大事件表 控制器
 */
@RestController
@RequestMapping("/api/timeline-event")
@RequiredArgsConstructor
@Tag(name = "时间轴大事件表管理")
public class TimelineEventController {

    private final TimelineEventService timelineEventService;

    @GetMapping("/list")
    @Operation(summary = "获取时间轴大事件表列表")
    public RestResult<IPage<TimelineEvent>> list(TimelineEvent timelineEvent) {
        return RestResult.success(timelineEventService.selectPage(timelineEvent));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取时间轴大事件表详情")
    public RestResult<TimelineEvent> getInfo(@PathVariable("id") Integer id) {
        return RestResult.success(timelineEventService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加时间轴大事件表")
    public RestResult<Object> add(@RequestBody TimelineEvent timelineEvent) {
        return RestResult.success(timelineEventService.insert(timelineEvent));
    }

    @PutMapping("/update")
    @Operation(summary = "修改时间轴大事件表")
    public RestResult<Object> edit(@RequestBody TimelineEvent timelineEvent) {
        return RestResult.success(timelineEventService.update(timelineEvent));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除时间轴大事件表")
    public RestResult<Object> remove(@PathVariable List<Integer> ids) {
        return RestResult.success(timelineEventService.deleteByIds(ids));
    }
}
