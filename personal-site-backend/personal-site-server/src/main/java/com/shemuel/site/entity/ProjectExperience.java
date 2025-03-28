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
@TableName("project_experience")
@Tag(name = "项目经验对象")
public class ProjectExperience implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(description = "")
    private Integer id;

    @Schema(description = "外键关联user_profile表的id")
    private Long userId;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "角色")
    private String role;

    @Schema(description = "开始日期")
    private LocalDateTime startDate;

    @Schema(description = "结束日期")
    private LocalDateTime endDate;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "项目链接")
    private String projectUrl;

    @Schema(description = "排序顺序")
    private Integer sortOrder;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
