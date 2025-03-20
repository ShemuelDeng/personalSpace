package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.UserProfile;
import com.shemuel.site.service.UserProfileService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 用户个人信息 控制器
 */
@RestController
@RequestMapping("/api/user-profile")
@RequiredArgsConstructor
@Tag(name = "用户个人信息管理")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/list")
    @Operation(summary = "获取用户个人信息列表")
    public RestResult<IPage<UserProfile>> list(UserProfile userProfile) {
        return RestResult.success(userProfileService.selectPage(userProfile));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取用户个人信息详情")
    public RestResult<UserProfile> getInfo(@PathVariable("id") Long id) {
        return RestResult.success(userProfileService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加用户个人信息")
    public RestResult<Object> add(@RequestBody UserProfile userProfile) {
        return RestResult.success(userProfileService.insert(userProfile));
    }

    @PutMapping("/update")
    @Operation(summary = "修改用户个人信息")
    public RestResult<Object> edit(@RequestBody UserProfile userProfile) {
        return RestResult.success(userProfileService.update(userProfile));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除用户个人信息")
    public RestResult<Object> remove(@PathVariable List<Long> ids) {
        return RestResult.success(userProfileService.deleteByIds(ids));
    }
}
