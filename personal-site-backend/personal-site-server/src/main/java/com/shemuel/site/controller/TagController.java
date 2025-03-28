package com.shemuel.site.controller;

import java.util.List;

import com.shemuel.site.entity.ArticleTag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.service.TagService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 文章标签表 控制器
 */
@RestController
@RequestMapping("/api/tag")
@RequiredArgsConstructor
@Tag(name = "文章标签表管理")
public class TagController {

    private final TagService tagService;

    @GetMapping("/list")
    @Operation(summary = "获取文章标签表列表")
    public RestResult<IPage<ArticleTag>> list(ArticleTag tag) {
        return RestResult.success(tagService.selectPage(tag));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取文章标签表详情")
    public RestResult<ArticleTag> getInfo(@PathVariable("id") Integer id) {
        return RestResult.success(tagService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加文章标签表")
    public RestResult<Object> add(@RequestBody ArticleTag tag) {
        return RestResult.success(tagService.insert(tag));
    }

    @PutMapping("/update")
    @Operation(summary = "修改文章标签表")
    public RestResult<Object> edit(@RequestBody ArticleTag tag) {
        return RestResult.success(tagService.update(tag));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除文章标签表")
    public RestResult<Object> remove(@PathVariable List<Integer> ids) {
        return RestResult.success(tagService.deleteByIds(ids));
    }
}
