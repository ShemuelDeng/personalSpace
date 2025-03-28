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
@TableName("certificates")
@Tag(name = "证书信息对象")
public class Certificates implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(description = "")
    private Integer id;

    @Schema(description = "外键关联user_profile表的id")
    private Long userId;

    @Schema(description = "证书名称")
    private String certificateName;

    @Schema(description = "颁发机构")
    private String issuingOrganization;

    @Schema(description = "颁发日期")
    private LocalDateTime issueDate;

    @Schema(description = "过期日期")
    private LocalDateTime expirationDate;

    @Schema(description = "证书ID")
    private String credentialId;

    @Schema(description = "证书链接")
    private String credentialUrl;

    @Schema(description = "排序顺序")
    private Integer sortOrder;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
