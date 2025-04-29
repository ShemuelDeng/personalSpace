package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.FileDetail;
import com.shemuel.site.service.FileDetailService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 文件记录表 控制器
 */
@RestController
@RequestMapping("/api/file-detail")
@RequiredArgsConstructor
@Tag(name = "文件记录表管理")
public class FileDetailController {

    private final FileDetailService fileDetailService;

    @GetMapping("/list")
    @Operation(summary = "获取文件记录表列表")
    public RestResult<IPage<FileDetail>> list(FileDetail fileDetail) {
        return RestResult.success(fileDetailService.selectPage(fileDetail));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取文件记录表详情")
    public RestResult<FileDetail> getInfo(@PathVariable("id") String id) {
        return RestResult.success(fileDetailService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加文件记录表")
    public RestResult<Object> add(@RequestBody FileDetail fileDetail) {
        return RestResult.success(fileDetailService.insert(fileDetail));
    }

    @PutMapping("/update")
    @Operation(summary = "修改文件记录表")
    public RestResult<Object> edit(@RequestBody FileDetail fileDetail) {
        return RestResult.success(fileDetailService.update(fileDetail));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除文件记录表")
    public RestResult<Object> remove(@PathVariable List<String> ids) {
        return RestResult.success(fileDetailService.deleteByIds(ids));
    }
}
