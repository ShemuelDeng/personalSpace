package com.shemuel.site.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Tag(name = "保存文章视图对象")
public class SaveArticleDTO implements Serializable {

    @Schema(description = "文章ID")
    private Integer id;

    @Schema(description = "作者ID")
    private Long userId;

    @Schema(description = "所属系列ID")
    private Integer seriesId;


    @Schema(description = "所属分类ID")
    private Integer categoryId;

    @Schema(description = "标签合集")
    private List<Integer> tagIds;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "内容摘要")
    private String summary;

    @Schema(description = "文章正文（Markdown格式）")
    private String content;

    @Schema(description = "文章HTML内容")
    private String htmlContent;

    @Schema(description = "封面图URL")
    private String coverImage;

    @Schema(description = "发布状态")
    private String status;

    @Schema(description = "浏览数")
    private Integer viewCount;

    @Schema(description = "点赞数")
    private Integer likeCount;

    @Schema(description = "收藏数")
    private Integer saveCount;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
