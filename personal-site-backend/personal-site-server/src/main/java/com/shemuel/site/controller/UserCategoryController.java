package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.UserCategory;
import com.shemuel.site.service.UserCategoryService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 用户私有分类 控制器
 */
@RestController
@RequestMapping("/api/user-category")
@RequiredArgsConstructor
@Tag(name = "用户私有分类管理")
public class UserCategoryController {

    private final UserCategoryService userCategoryService;

    @GetMapping("/list")
    @Operation(summary = "获取用户私有分类列表")
    public RestResult<IPage<UserCategory>> list(UserCategory userCategory) {
        return RestResult.success(userCategoryService.selectPage(userCategory));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取用户私有分类详情")
    public RestResult<UserCategory> getInfo(@PathVariable("id") Integer id) {
        return RestResult.success(userCategoryService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加用户私有分类")
    public RestResult<Object> add(@RequestBody UserCategory userCategory) {
        return RestResult.success(userCategoryService.insert(userCategory));
    }

    @PutMapping("/update")
    @Operation(summary = "修改用户私有分类")
    public RestResult<Object> edit(@RequestBody UserCategory userCategory) {
        return RestResult.success(userCategoryService.update(userCategory));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除用户私有分类")
    public RestResult<Object> remove(@PathVariable List<Integer> ids) {
        return RestResult.success(userCategoryService.deleteByIds(ids));
    }
}
