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
@TableName("article_tag_relation")
@Tag(name = "文章标签关联表对象")
public class ArticleTagRelation implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(description = "关联ID")
    private Integer id;

    @Schema(description = "文章ID")
    private Integer articleId;

    @Schema(description = "标签ID")
    private Integer tagId;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}
