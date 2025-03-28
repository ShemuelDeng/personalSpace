package com.shemuel.site.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.shemuel.site.mapper.CommentMapper;
import com.shemuel.site.entity.Comment;
import com.shemuel.site.service.CommentService;
import com.shemuel.site.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 文章评论表 服务实现类
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    /**
     * 查询文章评论表分页列表
     */
    @Override
    public IPage<Comment> selectPage(Comment comment) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(comment.getId() != null, Comment::getId, comment.getId());
        wrapper.eq(comment.getUserId() != null, Comment::getUserId, comment.getUserId());
        wrapper.eq(comment.getArticleId() != null, Comment::getArticleId, comment.getArticleId());
        wrapper.eq(comment.getParentId() != null, Comment::getParentId, comment.getParentId());
        wrapper.eq(comment.getContent() != null, Comment::getContent, comment.getContent());
        wrapper.eq(comment.getLikeCount() != null, Comment::getLikeCount, comment.getLikeCount());
        wrapper.eq(comment.getStatus() != null, Comment::getStatus, comment.getStatus());
        wrapper.eq(comment.getCreatedAt() != null, Comment::getCreatedAt, comment.getCreatedAt());
        wrapper.eq(comment.getUpdatedAt() != null, Comment::getUpdatedAt, comment.getUpdatedAt());
        return page(PageUtil.getPage(), wrapper);
    }

    /**
     * 查询文章评论表列表
     */
    @Override
    public List<Comment> selectList(Comment comment) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(comment.getId() != null, Comment::getId, comment.getId());
        wrapper.eq(comment.getUserId() != null, Comment::getUserId, comment.getUserId());
        wrapper.eq(comment.getArticleId() != null, Comment::getArticleId, comment.getArticleId());
        wrapper.eq(comment.getParentId() != null, Comment::getParentId, comment.getParentId());
        wrapper.eq(comment.getContent() != null, Comment::getContent, comment.getContent());
        wrapper.eq(comment.getLikeCount() != null, Comment::getLikeCount, comment.getLikeCount());
        wrapper.eq(comment.getStatus() != null, Comment::getStatus, comment.getStatus());
        wrapper.eq(comment.getCreatedAt() != null, Comment::getCreatedAt, comment.getCreatedAt());
        wrapper.eq(comment.getUpdatedAt() != null, Comment::getUpdatedAt, comment.getUpdatedAt());
        return list(wrapper);
    }

    /**
     * 新增文章评论表
     */
    @Override
    public boolean insert(Comment comment) {
        return save(comment);
    }

    /**
     * 修改文章评论表
     */
    @Override
    public boolean update(Comment comment) {
        return updateById(comment);
    }

    /**
     * 批量删除文章评论表
     */
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return removeByIds(ids);
    }
}
