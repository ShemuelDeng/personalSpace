package com.shemuel.site.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;

@Data
@TableName("article_tag")
@Tag(name = "文章标签关联表对象")
public class ArticleTagRelation implements Serializable {


    @Schema(name = "文章ID")
    private Integer articleId;

    @Schema(name = "标签ID")
    private Integer tagId;

    @Schema(name = "关联创建时间")
    private LocalDateTime createdAt;
}
