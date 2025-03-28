package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.UserTag;
import com.shemuel.site.service.UserTagService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 用户私有标签库 控制器
 */
@RestController
@RequestMapping("/api/user-tag")
@RequiredArgsConstructor
@Tag(name = "用户私有标签库管理")
public class UserTagController {

    private final UserTagService userTagService;

    @GetMapping("/list")
    @Operation(summary = "获取用户私有标签库列表")
    public RestResult<IPage<UserTag>> list(UserTag userTag) {
        return RestResult.success(userTagService.selectPage(userTag));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取用户私有标签库详情")
    public RestResult<UserTag> getInfo(@PathVariable("id") Integer id) {
        return RestResult.success(userTagService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加用户私有标签库")
    public RestResult<Object> add(@RequestBody UserTag userTag) {
        return RestResult.success(userTagService.insert(userTag));
    }

    @PutMapping("/update")
    @Operation(summary = "修改用户私有标签库")
    public RestResult<Object> edit(@RequestBody UserTag userTag) {
        return RestResult.success(userTagService.update(userTag));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除用户私有标签库")
    public RestResult<Object> remove(@PathVariable List<Integer> ids) {
        return RestResult.success(userTagService.deleteByIds(ids));
    }
}
