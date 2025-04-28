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
@TableName("timeline_event")
@Tag(name = "时间轴大事件表对象")
public class TimelineEvent implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "事件日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @Schema(description = "事件年份")
    private Integer eventYear;

    @Schema(description = "事件标题")
    private String title;

    @Schema(description = "事件内容（最长2000字）")
    private String content;

    @Schema(description = "发生位置")
    private String location;

    @Schema(description = "图片链接，支持多个，用逗号或JSON存")
    private String images;

    @Schema(description = "创建时间")
    private String createdAt;

    @Schema(description = "更新时间")
    private String updatedAt;
}
