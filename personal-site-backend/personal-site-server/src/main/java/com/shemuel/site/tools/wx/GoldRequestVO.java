package com.shemuel.site.tools.wx;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 请求对象
 */
@Data
@Accessors(chain = true)
public class GoldRequestVO {
    /**
     * 接收者openId
     */
    private String touser;
    /**
     * 模板id
     */
    private String template_id;
    /**
     * 微信接口默认地址
     */
    private String url = "http://weixin.qq.com/download";
    /**
     * 背景颜色
     */
    private String topcolor = "#FF0000";
    /**
     * 参数对象
     */
    private GoldPriceDataVO data;

}
