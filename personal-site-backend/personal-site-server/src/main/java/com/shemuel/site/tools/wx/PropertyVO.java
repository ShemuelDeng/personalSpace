package com.shemuel.site.tools.wx;

import lombok.Builder;
import lombok.Data;

/**
 * 属性设置
 */
@Data
@Builder
public class PropertyVO {

    /**
     * 值
     */
    private Object value;

    /**
     * 颜色
     */
    private String color;


    public static PropertyVO init(Object value,String color){
        return PropertyVO.builder().value(value).color(color).build();
    }
}
