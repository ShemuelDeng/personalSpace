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
@TableName("user_tag")
@Tag(name = "用户私有标签库对象")
public class UserTag implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(name = "私有标签ID")
    private Integer id;

    @Schema(name = "所属用户ID")
    private Long userId;

    @Schema(name = "标签名称")
    private String name;

    @Schema(name = "标签类型， 0：系统；1：用户")
    private Integer tagType;

    @Schema(name = "创建时间")
    private LocalDateTime createdAt;
}
