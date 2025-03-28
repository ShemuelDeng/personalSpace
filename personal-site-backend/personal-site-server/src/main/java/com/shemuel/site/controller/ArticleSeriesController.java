package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.ArticleSeries;
import com.shemuel.site.service.ArticleSeriesService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 文章专题系列 控制器
 */
@RestController
@RequestMapping("/api/article-series")
@RequiredArgsConstructor
@Tag(name = "文章专题系列管理")
public class ArticleSeriesController {

    private final ArticleSeriesService articleSeriesService;

    @GetMapping("/list")
    @Operation(summary = "获取文章专题系列列表")
    public RestResult<IPage<ArticleSeries>> list(ArticleSeries articleSeries) {
        return RestResult.success(articleSeriesService.selectPage(articleSeries));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取文章专题系列详情")
    public RestResult<ArticleSeries> getInfo(@PathVariable("id") Integer id) {
        return RestResult.success(articleSeriesService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加文章专题系列")
    public RestResult<Object> add(@RequestBody ArticleSeries articleSeries) {
        return RestResult.success(articleSeriesService.insert(articleSeries));
    }

    @PutMapping("/update")
    @Operation(summary = "修改文章专题系列")
    public RestResult<Object> edit(@RequestBody ArticleSeries articleSeries) {
        return RestResult.success(articleSeriesService.update(articleSeries));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除文章专题系列")
    public RestResult<Object> remove(@PathVariable List<Integer> ids) {
        return RestResult.success(articleSeriesService.deleteByIds(ids));
    }
}
