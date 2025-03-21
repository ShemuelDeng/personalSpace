package com.shemuel.site.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.util.SaResult;
import com.shemuel.site.common.RestResult;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: 公众号: 加瓦点灯
 * @Date: 2025-03-12-11:15
 * @Description:
 */
@RestControllerAdvice
@Configuration
public class RestExceptionController {

    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SaResult handleError(NotLoginException e) {
        return SaResult.code(401).setMsg("未登录或登录状态已过期");
    }

    @ExceptionHandler(ServiceException.class)
    public RestResult handleError(ServiceException e) {
        return RestResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public RestResult handleError(BusinessException e) {
        return RestResult.error(e.getCode(), e.getMessage());
    }
}
