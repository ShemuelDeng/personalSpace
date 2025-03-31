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
@TableName("third_party_platform")
@Tag(name = "第三方平台信息表对象")
public class ThirdPartyPlatform implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "平台：1-稀土掘金；2-今日头条；3-知乎；4-CSDN")
    private Integer platformType;

    @Schema(description = "平台名称：1-稀土掘金；2-今日头条；3-知乎；4-CSDN")
    private Integer platformName;

    @Schema(description = "创建草稿URL")
    private String createDraftUrl;

    @Schema(description = "更新草稿URL")
    private String updateDraftUrl;

    @Schema(description = "设置主题URL")
    private String setTopicUrl;

    @Schema(description = "发布文章URL")
    private String publishArticleUrl;

    @Schema(description = "请求头信息")
    private String header;

    @Schema(description = "cookie信息")
    private String cookie;
}
