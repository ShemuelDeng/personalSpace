package com.shemuel.site.service;

import com.shemuel.site.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 文章评论表 服务接口
 */
public interface CommentService extends IService<Comment> {
    /**
     * 查询文章评论表分页列表
     */
    IPage<Comment> selectPage(Comment comment);

    /**
     * 查询文章评论表列表
     */
    List<Comment> selectList(Comment comment);

    /**
     * 新增文章评论表
     */
    boolean insert(Comment comment);

    /**
     * 修改文章评论表
     */
    boolean update(Comment comment);

    /**
     * 批量删除文章评论表
     */
    boolean deleteByIds(List<Integer> ids);
}
