package com.seeu.core;

/**
 * 服务（业务）异常如“ 账号或密码错误 ”，该异常只做INFO级别的日志记录 @see WebMvcConfigurer
 */
public class ServiceException extends RuntimeException {

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ServiceException() {
        this.status = 500;
    }

    public ServiceException(String message) {
        super(message);
        this.status = 500;// 默认 500
    }

    public ServiceException(int status, String message) {
        super(message);
        this.status = status;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
