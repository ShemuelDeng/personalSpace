package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.Skills;
import com.shemuel.site.service.SkillsService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 技能信息 控制器
 */
@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
@Tag(name = "技能信息管理")
public class SkillsController {

    private final SkillsService skillsService;

    @GetMapping("/list")
    @Operation(summary = "获取技能信息列表")
    public RestResult<IPage<Skills>> list(Skills skills) {
        return RestResult.success(skillsService.selectPage(skills));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取技能信息详情")
    public RestResult<Skills> getInfo(@PathVariable("id") Integer id) {
        return RestResult.success(skillsService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加技能信息")
    public RestResult<Object> add(@RequestBody Skills skills) {
        return RestResult.success(skillsService.insert(skills));
    }

    @PutMapping("/update")
    @Operation(summary = "修改技能信息")
    public RestResult<Object> edit(@RequestBody Skills skills) {
        return RestResult.success(skillsService.update(skills));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除技能信息")
    public RestResult<Object> remove(@PathVariable List<Integer> ids) {
        return RestResult.success(skillsService.deleteByIds(ids));
    }
}
