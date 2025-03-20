package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.Education;
import com.shemuel.site.service.EducationService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 教育经历 控制器
 */
@RestController
@RequestMapping("/api/education")
@RequiredArgsConstructor
@Tag(name = "教育经历管理")
public class EducationController {

    private final EducationService educationService;

    @GetMapping("/list")
    @Operation(summary = "获取教育经历列表")
    public RestResult<IPage<Education>> list(Education education) {
        return RestResult.success(educationService.selectPage(education));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取教育经历详情")
    public RestResult<Education> getInfo(@PathVariable("id") Integer id) {
        return RestResult.success(educationService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加教育经历")
    public RestResult<Object> add(@RequestBody Education education) {
        return RestResult.success(educationService.insert(education));
    }

    @PutMapping("/update")
    @Operation(summary = "修改教育经历")
    public RestResult<Object> edit(@RequestBody Education education) {
        return RestResult.success(educationService.update(education));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除教育经历")
    public RestResult<Object> remove(@PathVariable List<Integer> ids) {
        return RestResult.success(educationService.deleteByIds(ids));
    }
}
