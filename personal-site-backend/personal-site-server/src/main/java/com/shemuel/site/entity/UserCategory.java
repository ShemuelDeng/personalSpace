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
@TableName("user_category")
@Tag(name = "用户私有分类对象")
public class UserCategory implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(name = "")
    private Integer id;

    @Schema(name = "")
    private Long userId;

    @Schema(name = "")
    private String name;

    @Schema(name = "")
    private String description;

    @Schema(name = "")
    private Integer parentId;

    @Schema(name = "")
    private LocalDateTime createdAt;
}
