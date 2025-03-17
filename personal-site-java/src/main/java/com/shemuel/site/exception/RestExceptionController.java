package com.shemuel.site.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.util.SaResult;
import com.shemuel.site.common.RestResult;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: dengshaoxiang
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

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RestResult handleError(BusinessException e) {
        return RestResult.data(e.getCode(), e.getMessage());
    }
}
