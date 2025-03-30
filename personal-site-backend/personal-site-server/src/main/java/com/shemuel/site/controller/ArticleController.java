package com.shemuel.site.controller;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import com.shemuel.site.dto.SaveArticleDTO;
import com.shemuel.site.service.ArticleSyncService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.Article;
import com.shemuel.site.service.ArticleService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 核心文章数据 控制器
 */
@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
@Tag(name = "核心文章数据管理")
public class ArticleController {

    private final ThreadPoolExecutor commonThreadPool;

    private final ArticleService articleService;
    private final ArticleSyncService articleSyncService;

    @GetMapping("/list")
    @Operation(summary = "获取核心文章数据列表")
    public RestResult<IPage<Article>> list(Article article) {
        return RestResult.success(articleService.selectPage(article));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取核心文章数据详情")
    public RestResult<Article> getInfo(@PathVariable("id") Integer id) {
        return RestResult.success(articleService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加核心文章数据")
    public RestResult<Object> add(@RequestBody SaveArticleDTO articleDTO) {
        return RestResult.success(articleService.insert(articleDTO));
    }

    @PutMapping("/update")
    @Operation(summary = "修改核心文章数据")
    public RestResult<Object> edit(@RequestBody Article article) {
        return RestResult.success(articleService.update(article));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除核心文章数据")
    public RestResult<Object> remove(@PathVariable List<Integer> ids) {
        return RestResult.success(articleService.deleteByIds(ids));
    }

    @PostMapping("/sync/csdn/{id}")
    @Operation(summary = "同步文章到CSDN,掘金,知乎平台")
    public RestResult<Object> syncToOtherPlatForm(@PathVariable("id") Integer id) {
        Article article = articleService.getById(id);
        if (article == null) {
            return RestResult.error("文章不存在");
        }
        commonThreadPool.execute(()->{
//            articleSyncService.syncToCSDN(article);
//            articleSyncService.syncToJuejin(article);
//            articleSyncService.syncToZhihu(article);
            articleSyncService.syncToTouTiao(article);
        });
        return RestResult.success("任务提交成功");
    }

}
