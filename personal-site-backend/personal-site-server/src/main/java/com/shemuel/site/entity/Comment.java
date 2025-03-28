package com.shemuel.site.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;

@Data
@TableName("comment")
@Tag(name = "文章评论表对象")
public class Comment implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(name = "评论ID")
    private Integer id;

    @Schema(name = "评论者ID")
    private Long userId;

    @Schema(name = "所属文章ID")
    private Integer articleId;

    @Schema(name = "父评论ID（实现评论回复）")
    private Integer parentId;

    @Schema(name = "评论内容")
    private String content;

    @Schema(name = "点赞数量")
    private Integer likeCount;

    @Schema(name = "评论状态")
    private String status;

    @Schema(name = "评论时间")
    private LocalDateTime createdAt;

    @Schema(name = "最后更新时间")
    private LocalDateTime updatedAt;
}
