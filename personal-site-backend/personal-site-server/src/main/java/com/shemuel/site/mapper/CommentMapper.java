package com.shemuel.site.mapper;

import com.shemuel.site.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章评论表 Mapper接口
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
} 