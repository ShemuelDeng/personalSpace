package com.shemuel.site.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;

@Data
@TableName("tag")
@Tag(name = "文章标签表对象")
public class ArticleTag implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(name = "标签ID")
    private Integer id;

    @Schema(name = "标签名称（唯一）")
    private String name;

    @Schema(name = "标签创建时间")
    private LocalDateTime createdAt;

    @Schema(name = "最后更新时间")
    private LocalDateTime updatedAt;
}
