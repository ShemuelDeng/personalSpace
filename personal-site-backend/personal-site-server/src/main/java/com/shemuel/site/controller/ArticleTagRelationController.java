package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.ArticleTagRelation;
import com.shemuel.site.service.ArticleTagRelationService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 文章标签关联表 控制器
 */
@RestController
@RequestMapping("/api/article-tag-relation")
@RequiredArgsConstructor
@Tag(name = "文章标签关联表管理")
public class ArticleTagRelationController {

    private final ArticleTagRelationService articleTagRelationService;

    @GetMapping("/list")
    @Operation(summary = "获取文章标签关联表列表")
    public RestResult<IPage<ArticleTagRelation>> list(ArticleTagRelation articleTagRelation) {
        return RestResult.success(articleTagRelationService.selectPage(articleTagRelation));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取文章标签关联表详情")
    public RestResult<ArticleTagRelation> getInfo(@PathVariable("id") Integer id) {
        return RestResult.success(articleTagRelationService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加文章标签关联表")
    public RestResult<Object> add(@RequestBody ArticleTagRelation articleTagRelation) {
        return RestResult.success(articleTagRelationService.insert(articleTagRelation));
    }

    @PutMapping("/update")
    @Operation(summary = "修改文章标签关联表")
    public RestResult<Object> edit(@RequestBody ArticleTagRelation articleTagRelation) {
        return RestResult.success(articleTagRelationService.update(articleTagRelation));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除文章标签关联表")
    public RestResult<Object> remove(@PathVariable List<Integer> ids) {
        return RestResult.success(articleTagRelationService.deleteByIds(ids));
    }
}
