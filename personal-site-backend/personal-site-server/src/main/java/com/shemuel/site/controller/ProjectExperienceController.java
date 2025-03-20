package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.ProjectExperience;
import com.shemuel.site.service.ProjectExperienceService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 项目经验 控制器
 */
@RestController
@RequestMapping("/api/project-experience")
@RequiredArgsConstructor
@Tag(name = "项目经验管理")
public class ProjectExperienceController {

    private final ProjectExperienceService projectExperienceService;

    @GetMapping("/list")
    @Operation(summary = "获取项目经验列表")
    public RestResult<IPage<ProjectExperience>> list(ProjectExperience projectExperience) {
        return RestResult.success(projectExperienceService.selectPage(projectExperience));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取项目经验详情")
    public RestResult<ProjectExperience> getInfo(@PathVariable("id") Integer id) {
        return RestResult.success(projectExperienceService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加项目经验")
    public RestResult<Object> add(@RequestBody ProjectExperience projectExperience) {
        return RestResult.success(projectExperienceService.insert(projectExperience));
    }

    @PutMapping("/update")
    @Operation(summary = "修改项目经验")
    public RestResult<Object> edit(@RequestBody ProjectExperience projectExperience) {
        return RestResult.success(projectExperienceService.update(projectExperience));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除项目经验")
    public RestResult<Object> remove(@PathVariable List<Integer> ids) {
        return RestResult.success(projectExperienceService.deleteByIds(ids));
    }
}
