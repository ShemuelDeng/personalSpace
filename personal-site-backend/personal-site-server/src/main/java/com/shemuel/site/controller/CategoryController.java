package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.Category;
import com.shemuel.site.service.CategoryService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 文章分类表 控制器
 */
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Tag(name = "文章分类表管理")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/list")
    @Operation(summary = "获取文章分类表列表")
    public RestResult<IPage<Category>> list(Category category) {
        return RestResult.success(categoryService.selectPage(category));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取文章分类表详情")
    public RestResult<Category> getInfo(@PathVariable("id") Integer id) {
        return RestResult.success(categoryService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加文章分类表")
    public RestResult<Object> add(@RequestBody Category category) {
        return RestResult.success(categoryService.insert(category));
    }

    @PutMapping("/update")
    @Operation(summary = "修改文章分类表")
    public RestResult<Object> edit(@RequestBody Category category) {
        return RestResult.success(categoryService.update(category));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除文章分类表")
    public RestResult<Object> remove(@PathVariable List<Integer> ids) {
        return RestResult.success(categoryService.deleteByIds(ids));
    }
}
