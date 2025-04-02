package com.shemuel.site.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: 公众号: 加瓦点灯
 * @Date: 2025-03-31-15:17
 * @Description:
 */
@Data
public class SyncArticleToOtherPlatFormRequest {

    @NotEmpty
    @NotNull
    @Schema(description = "平台：1-稀土掘金；2-今日头条；3-知乎；4-CSDN")
    private List<Integer> platformIds;

    @NotNull
    @Schema(description = "文章id")
    private Integer articleId;

}
