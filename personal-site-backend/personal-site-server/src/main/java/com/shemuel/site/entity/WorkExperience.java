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
@TableName("work_experience")
@Tag(name = "工作经历对象")
public class WorkExperience implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(name = "")
    private Integer id;

    @Schema(name = "外键关联user_profile表的id")
    private Long userId;

    @Schema(name = "公司名称")
    private String companyName;

    @Schema(name = "职位")
    private String position;

    @Schema(name = "开始日期")
    private LocalDateTime startDate;

    @Schema(name = "结束日期")
    private LocalDateTime endDate;

    @Schema(name = "是否正在该阶段")
    private Integer isCurrent;

    @Schema(name = "描述")
    private String description;

    @Schema(name = "排序顺序")
    private Integer sortOrder;

    @Schema(name = "创建时间")
    private LocalDateTime createdAt;

    @Schema(name = "更新时间")
    private LocalDateTime updatedAt;
}
