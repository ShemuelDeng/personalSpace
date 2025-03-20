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
    @Schema(name = "")
    private Integer id;

    @Schema(name = "外键关联user_profile表的id")
    private Long userId;

    @Schema(name = "项目名称")
    private String projectName;

    @Schema(name = "角色")
    private String role;

    @Schema(name = "开始日期")
    private LocalDateTime startDate;

    @Schema(name = "结束日期")
    private LocalDateTime endDate;

    @Schema(name = "描述")
    private String description;

    @Schema(name = "项目链接")
    private String projectUrl;

    @Schema(name = "排序顺序")
    private Integer sortOrder;

    @Schema(name = "创建时间")
    private LocalDateTime createdAt;

    @Schema(name = "更新时间")
    private LocalDateTime updatedAt;
}
