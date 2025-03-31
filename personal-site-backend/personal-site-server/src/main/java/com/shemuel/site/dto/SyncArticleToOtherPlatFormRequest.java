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
    private List<Integer> platformIds;

    @NotNull
    private Integer articleId;

}
