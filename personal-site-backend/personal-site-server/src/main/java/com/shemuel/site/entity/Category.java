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
@TableName("category")
@Tag(name = "文章分类表对象")
public class Category implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(name = "分类ID")
    private Integer id;

    @Schema(name = "分类名称（唯一）")
    private String name;

    @Schema(name = "分类描述说明")
    private String description;

    @Schema(name = "父分类ID（建立树形结构）")
    private Integer parentId;

    @Schema(name = "分类创建时间")
    private LocalDateTime createdAt;

    @Schema(name = "最后更新时间")
    private LocalDateTime updatedAt;
}
