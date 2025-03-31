package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.ThirdPartyPlatform;
import com.shemuel.site.service.ThirdPartyPlatformService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 第三方平台信息表 控制器
 */
@RestController
@RequestMapping("/api/third-party-platform")
@RequiredArgsConstructor
@Tag(name = "第三方平台信息表管理")
public class ThirdPartyPlatformController {

    private final ThirdPartyPlatformService thirdPartyPlatformService;

    @GetMapping("/list")
    @Operation(summary = "获取第三方平台信息表列表")
    public RestResult<IPage<ThirdPartyPlatform>> list(ThirdPartyPlatform thirdPartyPlatform) {
        return RestResult.success(thirdPartyPlatformService.selectPage(thirdPartyPlatform));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取第三方平台信息表详情")
    public RestResult<ThirdPartyPlatform> getInfo(@PathVariable("id") Integer id) {
        return RestResult.success(thirdPartyPlatformService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加第三方平台信息表")
    public RestResult<Object> add(@RequestBody ThirdPartyPlatform thirdPartyPlatform) {
        return RestResult.success(thirdPartyPlatformService.insert(thirdPartyPlatform));
    }

    @PutMapping("/update")
    @Operation(summary = "修改第三方平台信息表")
    public RestResult<Object> edit(@RequestBody ThirdPartyPlatform thirdPartyPlatform) {
        return RestResult.success(thirdPartyPlatformService.update(thirdPartyPlatform));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除第三方平台信息表")
    public RestResult<Object> remove(@PathVariable List<Integer> ids) {
        return RestResult.success(thirdPartyPlatformService.deleteByIds(ids));
    }
}
