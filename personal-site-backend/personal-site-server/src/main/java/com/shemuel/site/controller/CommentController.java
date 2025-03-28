package com.shemuel.site.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.shemuel.site.entity.Comment;
import com.shemuel.site.service.CommentService;
import com.shemuel.site.common.RestResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 文章评论表 控制器
 */
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Tag(name = "文章评论表管理")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/list")
    @Operation(summary = "获取文章评论表列表")
    public RestResult<IPage<Comment>> list(Comment comment) {
        return RestResult.success(commentService.selectPage(comment));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取文章评论表详情")
    public RestResult<Comment> getInfo(@PathVariable("id") Integer id) {
        return RestResult.success(commentService.getById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "添加文章评论表")
    public RestResult<Object> add(@RequestBody Comment comment) {
        return RestResult.success(commentService.insert(comment));
    }

    @PutMapping("/update")
    @Operation(summary = "修改文章评论表")
    public RestResult<Object> edit(@RequestBody Comment comment) {
        return RestResult.success(commentService.update(comment));
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "删除文章评论表")
    public RestResult<Object> remove(@PathVariable List<Integer> ids) {
        return RestResult.success(commentService.deleteByIds(ids));
    }
}
