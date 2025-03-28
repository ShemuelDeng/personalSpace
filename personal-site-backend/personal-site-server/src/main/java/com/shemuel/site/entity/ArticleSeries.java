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
@TableName("article_series")
@Tag(name = "文章专题系列对象")
public class ArticleSeries implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(description = "系列ID")
    private Integer id;

    @Schema(description = "作者ID")
    private Long userId;

    @Schema(description = "系列名称")
    private String name;

    @Schema(description = "URL标识（全站唯一）")
    private String slug;

    @Schema(description = "系列描述")
    private String description;

    @Schema(description = "封面图URL")
    private String coverImage;

    @Schema(description = "发布状态")
    private String status;

    @Schema(description = "排序序号")
    private Integer orderNum;

    @Schema(description = "包含文章数")
    private Integer articleCount;

    @Schema(description = "订阅人数")
    private Integer subscribers;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
