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
@TableName("education")
@Tag(name = "教育经历对象")
public class Education implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(description = "")
    private Integer id;

    @Schema(description = "外键关联user_profile表的id")
    private Long userId;

    @Schema(description = "学校名称")
    private String schoolName;

    @Schema(description = "专业")
    private String major;

    @Schema(description = "学位")
    private String degree;

    @Schema(description = "开始日期")
    private LocalDateTime startDate;

    @Schema(description = "结束日期")
    private LocalDateTime endDate;

    @Schema(description = "是否正在该阶段")
    private Integer isCurrent;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "排序顺序")
    private Integer sortOrder;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
