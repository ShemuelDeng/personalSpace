package com.shemuel.site.common;

import cn.dev33.satoken.util.SaResult;

import javax.swing.plaf.SpinnerUI;
import java.util.Objects;

/**
 * @Author: dengshaoxiang
 * @Date: 2025-03-12-11:19
 * @Description:
 */
public class RestResult extends SaResult {

    public RestResult() {
        super();
    }

    public RestResult(int code, String msg, Object data) {
        super(code, msg, data);
    }

    public static RestResult data(int code,String msg) {
        return new RestResult(code,msg,null);
    }

    public static RestResult success(Object data) {
        return new RestResult(200,"success",data);
    }
}
