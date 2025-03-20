package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.WorkExperience;
import com.shemuel.site.service.WorkExperienceService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 工作经历 控制器
 */
@RestController
@RequestMapping("/api/work-experience")
@RequiredArgsConstructor
@Tag(name = "工作经历管理")
public class WorkExperienceController {

    private final WorkExperienceService workExperienceService;

    @GetMapping("/list")
    @Operation(summary = "获取工作经历列表")
    public RestResult<IPage<WorkExperience>> list(WorkExperience workExperience) {
        return RestResult.success(workExperienceService.selectPage(workExperience));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取工作经历详情")
    public RestResult<WorkExperience> getInfo(@PathVariable("id") Integer id) {
        return RestResult.success(workExperienceService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加工作经历")
    public RestResult<Object> add(@RequestBody WorkExperience workExperience) {
        return RestResult.success(workExperienceService.insert(workExperience));
    }

    @PutMapping("/update")
    @Operation(summary = "修改工作经历")
    public RestResult<Object> edit(@RequestBody WorkExperience workExperience) {
        return RestResult.success(workExperienceService.update(workExperience));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除工作经历")
    public RestResult<Object> remove(@PathVariable List<Integer> ids) {
        return RestResult.success(workExperienceService.deleteByIds(ids));
    }
}
