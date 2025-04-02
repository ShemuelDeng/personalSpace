package com.shemuel.site.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName(value = "article_sync_record", excludeProperty = {"platformName"})
@Tag(name = "文章同步记录表对象")
public class ArticleSyncRecord implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "文章ID")
    private String articleId;

    @Schema(description = "文章标题")
    private String articleTitle;

    @Schema(description = "平台ID，关联third_party_platform.id")
    private Integer platformId;

    @Schema(description = "平台名称")
    private String platformName;

    @Schema(description = "同步结果：0-失败；1-成功")
    private Integer syncResult;

    @Schema(description = "同步失败原因（仅当sync_result=0时有效）")
    private String syncFailReason;

    @Schema(description = "同步时间")
    private LocalDateTime syncTime;
}
