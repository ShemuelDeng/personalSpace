package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.ArticleTagRelation;
import com.shemuel.site.service.ArticleTagService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 文章标签关联表 控制器
 */
@RestController
@RequestMapping("/api/article-tag")
@RequiredArgsConstructor
@Tag(name = "文章标签关联表管理")
public class ArticleTagController {

    private final ArticleTagService articleTagService;

    @GetMapping("/list")
    @Operation(summary = "获取文章标签关联表列表")
    public RestResult<IPage<ArticleTagRelation>> list(ArticleTagRelation articleTagRelation) {
        return RestResult.success(articleTagService.selectPage(articleTagRelation));
    }

    @GetMapping("/{articleId}")
    @Operation(summary = "获取文章标签关联表详情")
    public RestResult<ArticleTagRelation> getInfo(@PathVariable("articleId") Integer articleId) {
        return RestResult.success(articleTagService.getById(articleId));
    }

    @PostMapping("/add")
    @Operation(summary = "添加文章标签关联表")
    public RestResult<Object> add(@RequestBody ArticleTagRelation articleTagRelation) {
        return RestResult.success(articleTagService.insert(articleTagRelation));
    }

    @PutMapping("/update")
    @Operation(summary = "修改文章标签关联表")
    public RestResult<Object> edit(@RequestBody ArticleTagRelation articleTagRelation) {
        return RestResult.success(articleTagService.update(articleTagRelation));
    }

    @DeleteMapping("/delete/{articleIds}")
    @Operation(summary = "删除文章标签关联表")
    public RestResult<Object> remove(@PathVariable List<Integer> articleIds) {
        return RestResult.success(articleTagService.deleteByIds(articleIds));
    }
}
