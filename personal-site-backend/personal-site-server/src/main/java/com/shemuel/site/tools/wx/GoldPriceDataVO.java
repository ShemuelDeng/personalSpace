package com.shemuel.site.tools.wx;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 每日金价推送数据
 */
@Data
@Accessors(chain = true)
public class GoldPriceDataVO {
    /**
     * 日期
     */
    private PropertyVO date;

    /**
     * 大盘金价
     */
    private PropertyVO basePrice;
    /**
     * 周大福
     */
    private PropertyVO zhoudafu;
    /**
     * 老凤祥
     */
    private PropertyVO laofengxiang;
    /**
     * 周六福
     */
    private PropertyVO zhouliufu;
    /**
     * 周生生
     */
    private PropertyVO zhoushengsheng;
    /**
     * 六福珠宝
     */
    private PropertyVO liufuzhubao;
    /**
     * 老庙黄金
     */
    private PropertyVO laomiaohuangjin;

}
