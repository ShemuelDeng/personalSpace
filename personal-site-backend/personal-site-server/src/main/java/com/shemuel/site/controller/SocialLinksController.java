package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.SocialLinks;
import com.shemuel.site.service.SocialLinksService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 社交连接信息 控制器
 */
@RestController
@RequestMapping("/api/social-links")
@RequiredArgsConstructor
@Tag(name = "社交连接信息管理")
public class SocialLinksController {

    private final SocialLinksService socialLinksService;

    @GetMapping("/list")
    @Operation(summary = "获取社交连接信息列表")
    public RestResult<IPage<SocialLinks>> list(SocialLinks socialLinks) {
        return RestResult.success(socialLinksService.selectPage(socialLinks));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取社交连接信息详情")
    public RestResult<SocialLinks> getInfo(@PathVariable("id") Integer id) {
        return RestResult.success(socialLinksService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加社交连接信息")
    public RestResult<Object> add(@RequestBody SocialLinks socialLinks) {
        return RestResult.success(socialLinksService.insert(socialLinks));
    }

    @PutMapping("/update")
    @Operation(summary = "修改社交连接信息")
    public RestResult<Object> edit(@RequestBody SocialLinks socialLinks) {
        return RestResult.success(socialLinksService.update(socialLinks));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除社交连接信息")
    public RestResult<Object> remove(@PathVariable List<Integer> ids) {
        return RestResult.success(socialLinksService.deleteByIds(ids));
    }
}
