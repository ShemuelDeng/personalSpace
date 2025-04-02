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
@TableName("third_party_platform_auth_info")
@Tag(name = "第三方平台认证信息表对象")
public class ThirdPartyPlatformAuthInfo implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "平台ID，关联third_party_platform.id, 1: 稀土掘金； 2： 今日头条；3： 知乎；4：csdn")
    private Integer platformId;

    @Schema(description = "请求头信息")
    private String header;

    @Schema(description = "cookie信息")
    private String cookie;
}
