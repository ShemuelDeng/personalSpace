package com.shemuel.site.tools.wx;

import lombok.Data;

/**
 * @Author: 公众号: 加瓦点灯
 * @Date: 2025-03-31-14:52
 * @Description:
 */
@Data
public class AccessTokenDTO {

    /**
     * token
     */
    private String access_token;

    /**
     * 过期时间
     */
    private String expires_in;

}
