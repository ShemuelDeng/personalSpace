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
@TableName("article")
@Tag(name = "核心文章数据对象")
public class Article implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(name = "文章ID")
    private Integer id;

    @Schema(name = "作者ID")
    private Long userId;

    @Schema(name = "分类ID")
    private Integer categoryId;

    @Schema(name = "所属系列ID")
    private Integer seriesId;

    @Schema(name = "文章标题")
    private String title;

    @Schema(name = "内容摘要")
    private String summary;

    @Schema(name = "文章正文")
    private String content;

    @Schema(name = "封面图URL")
    private String coverImage;

    @Schema(name = "发布状态")
    private String status;

    @Schema(name = "浏览数")
    private Integer viewCount;

    @Schema(name = "点赞数")
    private Integer likeCount;

    @Schema(name = "创建时间")
    private LocalDateTime createdAt;

    @Schema(name = "更新时间")
    private LocalDateTime updatedAt;
}
