package com.shemuel.site.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RestResult<T> {

    @Schema(name = "状态码")
    private Integer code;

    @Schema(name = "消息")
    private String message;

    @Schema(name = "数据")
    private T data;

    @Schema(name = "额外信息")
    private Map<String,Object> extra = new HashMap<>();

    public RestResult<T> putExtra(String key, Object value) {
        this.extra.put(key, value);
        return this;
    }

    public static <T> RestResult<T> success(T data) {
        RestResult<T> restResult = new RestResult<>();
        restResult.setCode(200);
        restResult.setMessage("success");
        restResult.setData(data);
        return restResult;
    }
    public static <T> RestResult<T> success() {
        RestResult<T> restResult = new RestResult<>();
        restResult.setCode(200);
        restResult.setMessage("success");
        restResult.setData(null);
        return restResult;
    }

    public static <T> RestResult<T> error(String message) {
        RestResult<T> restResult = new RestResult<>();
        restResult.setCode(500);
        restResult.setMessage(message);
        return restResult;
    }
    public static <T> RestResult<T> error(Integer code, String message) {
        RestResult<T> restResult = new RestResult<>();
        restResult.setCode(code);
        restResult.setMessage(message);
        return restResult;
    }
}
