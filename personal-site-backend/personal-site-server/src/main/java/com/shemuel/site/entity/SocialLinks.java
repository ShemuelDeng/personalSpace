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
@TableName("social_links")
@Tag(name = "社交连接信息对象")
public class SocialLinks implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(description = "")
    private Integer id;

    @Schema(description = "外键关联user_profile表的id")
    private Long userId;

    @Schema(description = "平台名称")
    private String platform;

    @Schema(description = "链接地址")
    private String url;

    @Schema(description = "排序顺序")
    private Integer sortOrder;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
