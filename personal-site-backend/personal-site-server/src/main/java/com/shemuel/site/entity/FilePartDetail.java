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
@TableName("file_part_detail")
@Tag(name = "文件分片信息表，仅在手动分片上传时使用对象")
public class FilePartDetail implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(description = "分片id")
    private String id;

    @Schema(description = "存储平台")
    private String platform;

    @Schema(description = "上传ID，仅在手动分片上传时使用")
    private String uploadId;

    @Schema(description = "分片 ETag")
    private String eTag;

    @Schema(description = "分片号。每一个上传的分片都有一个分片号，一般情况下取值范围是1~10000")
    private Integer partNumber;

    @Schema(description = "文件大小，单位字节")
    private Long partSize;

    @Schema(description = "哈希信息")
    private String hashInfo;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
