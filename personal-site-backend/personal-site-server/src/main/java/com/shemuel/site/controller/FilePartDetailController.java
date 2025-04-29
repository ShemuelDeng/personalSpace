package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.FilePartDetail;
import com.shemuel.site.service.FilePartDetailService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 文件分片信息表，仅在手动分片上传时使用 控制器
 */
@RestController
@RequestMapping("/api/file-part-detail")
@RequiredArgsConstructor
@Tag(name = "文件分片信息表，仅在手动分片上传时使用管理")
public class FilePartDetailController {

    private final FilePartDetailService filePartDetailService;

    @GetMapping("/list")
    @Operation(summary = "获取文件分片信息表，仅在手动分片上传时使用列表")
    public RestResult<IPage<FilePartDetail>> list(FilePartDetail filePartDetail) {
        return RestResult.success(filePartDetailService.selectPage(filePartDetail));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取文件分片信息表，仅在手动分片上传时使用详情")
    public RestResult<FilePartDetail> getInfo(@PathVariable("id") String id) {
        return RestResult.success(filePartDetailService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加文件分片信息表，仅在手动分片上传时使用")
    public RestResult<Object> add(@RequestBody FilePartDetail filePartDetail) {
        return RestResult.success(filePartDetailService.insert(filePartDetail));
    }

    @PutMapping("/update")
    @Operation(summary = "修改文件分片信息表，仅在手动分片上传时使用")
    public RestResult<Object> edit(@RequestBody FilePartDetail filePartDetail) {
        return RestResult.success(filePartDetailService.update(filePartDetail));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除文件分片信息表，仅在手动分片上传时使用")
    public RestResult<Object> remove(@PathVariable List<String> ids) {
        return RestResult.success(filePartDetailService.deleteByIds(ids));
    }
}
