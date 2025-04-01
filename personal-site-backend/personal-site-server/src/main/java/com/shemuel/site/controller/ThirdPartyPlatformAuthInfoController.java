package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.ThirdPartyPlatformAuthInfo;
import com.shemuel.site.service.ThirdPartyPlatformAuthInfoService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 第三方平台认证信息表 控制器
 */
@RestController
@RequestMapping("/api/third-party-platform-auth-info")
@RequiredArgsConstructor
@Tag(name = "第三方平台认证信息表管理")
public class ThirdPartyPlatformAuthInfoController {

    private final ThirdPartyPlatformAuthInfoService thirdPartyPlatformAuthInfoService;

    @GetMapping("/list")
    @Operation(summary = "获取第三方平台认证信息表列表")
    public RestResult<IPage<ThirdPartyPlatformAuthInfo>> list(ThirdPartyPlatformAuthInfo thirdPartyPlatformAuthInfo) {
        return RestResult.success(thirdPartyPlatformAuthInfoService.selectPage(thirdPartyPlatformAuthInfo));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取第三方平台认证信息表详情")
    public RestResult<ThirdPartyPlatformAuthInfo> getInfo(@PathVariable("id") Integer id) {
        return RestResult.success(thirdPartyPlatformAuthInfoService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加第三方平台认证信息表")
    public RestResult<Object> add(@RequestBody ThirdPartyPlatformAuthInfo thirdPartyPlatformAuthInfo) {
        return RestResult.success(thirdPartyPlatformAuthInfoService.insert(thirdPartyPlatformAuthInfo));
    }

    @PutMapping("/update")
    @Operation(summary = "修改第三方平台认证信息表")
    public RestResult<Object> edit(@RequestBody ThirdPartyPlatformAuthInfo thirdPartyPlatformAuthInfo) {
        return RestResult.success(thirdPartyPlatformAuthInfoService.update(thirdPartyPlatformAuthInfo));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除第三方平台认证信息表")
    public RestResult<Object> remove(@PathVariable List<Integer> ids) {
        return RestResult.success(thirdPartyPlatformAuthInfoService.deleteByIds(ids));
    }
}
