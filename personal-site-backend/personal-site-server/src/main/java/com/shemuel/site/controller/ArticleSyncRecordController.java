package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.ArticleSyncRecord;
import com.shemuel.site.service.ArticleSyncRecordService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 文章同步记录表 控制器
 */
@RestController
@RequestMapping("/api/article-sync-record")
@RequiredArgsConstructor
@Tag(name = "文章同步记录表管理")
public class ArticleSyncRecordController {

    private final ArticleSyncRecordService articleSyncRecordService;

    @GetMapping("/list")
    @Operation(summary = "获取文章同步记录表列表")
    public RestResult<IPage<ArticleSyncRecord>> list(ArticleSyncRecord articleSyncRecord) {
        return RestResult.success(articleSyncRecordService.selectPage(articleSyncRecord));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取文章同步记录表详情")
    public RestResult<ArticleSyncRecord> getInfo(@PathVariable("id") Integer id) {
        return RestResult.success(articleSyncRecordService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加文章同步记录表")
    public RestResult<Object> add(@RequestBody ArticleSyncRecord articleSyncRecord) {
        return RestResult.success(articleSyncRecordService.insert(articleSyncRecord));
    }

    @PutMapping("/update")
    @Operation(summary = "修改文章同步记录表")
    public RestResult<Object> edit(@RequestBody ArticleSyncRecord articleSyncRecord) {
        return RestResult.success(articleSyncRecordService.update(articleSyncRecord));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除文章同步记录表")
    public RestResult<Object> remove(@PathVariable List<Integer> ids) {
        return RestResult.success(articleSyncRecordService.deleteByIds(ids));
    }
}
