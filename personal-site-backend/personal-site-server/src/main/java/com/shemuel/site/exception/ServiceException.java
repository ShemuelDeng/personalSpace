package com.shemuel.site.exception;

/**
 * @Author: 公众号: 加瓦点灯
 * @Date: 2025-03-13-15:36
 * @Description:
 */
public class ServiceException extends RuntimeException{
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ServiceException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServiceException(String message) {
        this.code = 500;
        this.message = message;
    }

    public ServiceException() {
        this.code = 500;
        this.message = "服务异常";
    }
}
